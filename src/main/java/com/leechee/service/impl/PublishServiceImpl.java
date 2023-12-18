package com.leechee.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.leechee.constant.FrameLengthConstant;
import com.leechee.constant.MessageConstant;
import com.leechee.context.BaseContext;
import com.leechee.dto.DeleteVideoDTO;
import com.leechee.dto.FavoriteSearchDTO;
import com.leechee.dto.RelationSearchDTO;
import com.leechee.dto.UserInfoDTO;
import com.leechee.entity.Favorites;
import com.leechee.entity.Relations;
import com.leechee.entity.Users;
import com.leechee.entity.Videos;
import com.leechee.exception.PublishException;
import com.leechee.mapper.CommentMapper;
import com.leechee.mapper.FavoriteMapper;
import com.leechee.mapper.RelationMapper;
import com.leechee.mapper.UserMapper;
import com.leechee.mapper.VideoMapper;
import com.leechee.service.PublishService;
import com.leechee.vo.UserVO;
import com.leechee.vo.VideoVO;

@Service
public class PublishServiceImpl implements PublishService{

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 上传视频
     * @param filePath
     * @param title
     */
    @Transactional
    @Override
    public void action(String filePath, String coverPath, String title) {

        Long currentId = BaseContext.getCurrentId();
        Videos videos = Videos.builder()
               .title(title)
               .user_id(currentId)
               .play_url(filePath)
               .cover_url(coverPath)
               .create_time(LocalDateTime.now())
               .build();
        videoMapper.insert(videos);

        // 同步更新用户表中的作品数量
        userMapper.updateWorkCount(currentId, 1);
    }

    /**
     * 获取用户发布列表
     * @param userInfoDTO
     * @return
     */
    @Override
    public List<VideoVO> list(UserInfoDTO userInfoDTO) {
        // 从数据库获取用户视频列表
        List<Videos> videosList = videoMapper.getByUserId(userInfoDTO.getUser_id());   
        List<VideoVO> videoVOList = new ArrayList<>();
        
        for (Videos videos: videosList) {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(videos, videoVO);

            // 根据视频作者id查询用户信息
            Users videoAuthor = userMapper.getById(videos.getUser_id());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(videoAuthor, userVO);

            // 设置关注与点赞信息
            Long currentId = BaseContext.getCurrentId();

            // 查找关注信息
            RelationSearchDTO relationSearchDTO = new RelationSearchDTO();
            relationSearchDTO.setFrom_user_id(currentId);
            relationSearchDTO.setTo_user_id(videos.getUser_id());
            List<Relations> relations = relationMapper.getById(relationSearchDTO);
            if (relations != null && relations.size() > 0) {
                userVO.set_follow(true);
            } else {
                userVO.set_follow(false);
            }

            // 查找点赞信息
            FavoriteSearchDTO favoriteSearchDTO = new FavoriteSearchDTO();
            favoriteSearchDTO.setUser_id(currentId);
            favoriteSearchDTO.setVideo_id(videos.getId());
            List<Favorites> favorites = favoriteMapper.getById(favoriteSearchDTO);
            if (favorites != null && favorites.size() > 0) {
                videoVO.set_favorite(true);
            } else {
                videoVO.set_favorite(false);
            }

            videoVO.setAuthor(userVO);
            videoVOList.add(videoVO);
        }
        
        return videoVOList;
    }

    /**
     * 抽帧: 根据视频文件获得封面文件
     * @param file
     * @return
     */
    @Override
    public MultipartFile getCoverFile(MultipartFile file) {

        MultipartFile coverFile = null;
        
        try {
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);

            grabber.start();

            // 获取视频的总帧数
            int videoLength = grabber.getLengthInFrames();

            Frame frame = null;
            int i = 0;
            while (i < videoLength) {
                frame = grabber.grabImage();
                if (i > FrameLengthConstant.FRAME_LENGTH && frame.image != null) break;
                i++;
            }

            // 绘制图片
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bufferedImage = converter.getBufferedImage(frame);

            // 转为multipartfile
            coverFile = bufferedImageToMultipartFile(bufferedImage);

            grabber.close();

        } catch (Exception e) {
            throw new PublishException(MessageConstant.IMAGE_FILE_UPLOAD_FILED);
        }

        return coverFile;
    }
    
    /**
     * BufferedImage 转 MultipartFile
     * @param bufferedImage
     * @return
     */
    private MultipartFile bufferedImageToMultipartFile(BufferedImage bufferedImage) {
        MultipartFile cover = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            InputStream input = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            cover = new MockMultipartFile("coverFile", "coverfile.jpg", "multipart/form-data", input);
        } catch (Exception e) {
            throw new PublishException(MessageConstant.FAILED_TO_CONVERT_MULTI);
        }
        return cover;
    }


    /**
     * 删除视频
     * @param deleteVideoDTO
     * @return
     */
    @Transactional
    @Override
    public void deleteVideo(DeleteVideoDTO deleteVideoDTO) {
        Long currentId = BaseContext.getCurrentId();
        Long videoId = deleteVideoDTO.getVideo_id();

        // 判断该用户是否上传过该视频
        Videos videosDB = videoMapper.getById(videoId);
        if (videosDB != null && videosDB.getUser_id().equals(currentId)) {

            // videos删除一条记录
            videoMapper.deleteById(videoId);

            // comments删除videoId的全部记录
            commentMapper.deleteByVideoId(videoId);

            // favorites删除videoId的全部记录，同时统计对应的userId
            List<Long> userList = favoriteMapper.getUserIdByVideoId(videoId);
            favoriteMapper.deleteByVideoId(videoId);

            // users直接更新workCount，totalFavorited，根据统计的userId列表更新favoriteCount
            userMapper.updateWorkCount(currentId, -1);
            userMapper.updateTotalFavorited(currentId, -1 * videosDB.getFavorite_count());
            if (userList != null && userList.size() > 0)
                userMapper.updateFavoriteCountBatch(userList, (long) -1);

        } else {
            // 该用户上传的视频不存在
            throw new PublishException(MessageConstant.VIDEO_NOT_EXIST);
        }
    }
}
