package org.hds.Graphics;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;





import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class grabberVideoFramer {

	//视频文件路径
    //private static String videoPath = "E:/test/123.mp4";  
	
	/**
     * 
     * 功能说明:将视频按时间转换为BufferedImage集合
     * @param iplImage
     * javacv图像
     * @return BufferedImage集合
     *
     */
	public static List<BufferedImage> GetFramers(String videoPath,int width,int height,double stayTime) {
		List<BufferedImage> bufferedImages =new ArrayList<BufferedImage>();
		try {
		
		CvCapture capture = opencv_highgui.cvCreateFileCapture(videoPath);
		
		//帧率
		int fps = (int) opencv_highgui.cvGetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FPS);
		System.out.println("帧率:"+fps);
		
		IplImage frame = null;
		double pos1 = 0;
		
		int rootCount = 0;
 
		while (true) {
			
			//读取关键帧
			frame = opencv_highgui.cvQueryFrame(capture);
			
			rootCount = (int)(fps * stayTime);
			while(rootCount > 0 ){
				//这一段的目的是跳过每一秒钟的帧数,也就是说fps是帧率(一秒钟有多少帧),在读取一帧后,跳过fps数量的帧就相当于跳过了1秒钟。
				frame = opencv_highgui.cvQueryFrame(capture);
				rootCount--;
				if (null == frame)
				{				
					break;
				}
			}
 
			//获取当前帧的位置
			pos1 = opencv_highgui.cvGetCaptureProperty(capture,opencv_highgui.CV_CAP_PROP_POS_FRAMES);
			System.out.println(pos1);
 
			if (null == frame)
			{				
				break;
			}
			BufferedImage image = iplToBufferedImage(frame,width,height);
			bufferedImages.add(image);
			//opencv_highgui.cvSaveImage("d:\\" + pos1 + ".jpg",frame);
			ImageIO.write(image, "PNG", new File("E:/"+pos1+".png"));
			
		}
 
		opencv_highgui.cvReleaseCapture(capture);
		return bufferedImages;
		} catch (Exception e) {
			// TODO: handle exception
			return bufferedImages;
		}
	}
	
	/**
     * 
     * 功能说明:将视频按时间转换为BufferedImage集合
     * @param iplImage
     * javacv图像
     * @return BufferedImage集合
     *
     */
	public static BufferedImage GetFirstFramer(String videoPath) {
		BufferedImage bufferedImage = null;
		try {
		
		CvCapture capture = opencv_highgui.cvCreateFileCapture(videoPath);
		
		//帧率
		int fps = (int) opencv_highgui.cvGetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FPS);
		System.out.println("帧率:"+fps);
		
		IplImage frame = null;
		double pos1 = 0;
		
		int rootCount = 0;
 
		//读取关键帧
		frame = opencv_highgui.cvQueryFrame(capture);
		
		rootCount = fps;
		while(rootCount > 0 ){
		//这一段的目的是跳过每一秒钟的帧数,也就是说fps是帧率(一秒钟有多少帧),在读取一帧后,跳过fps数量的帧就相当于跳过了1秒钟。
				frame = opencv_highgui.cvQueryFrame(capture);
				rootCount--;
			}
 
			//获取当前帧的位置
			pos1 = opencv_highgui.cvGetCaptureProperty(capture,opencv_highgui.CV_CAP_PROP_POS_FRAMES);
			System.out.println(pos1);
 
			bufferedImage = iplToBufferedImage(frame);
 
		opencv_highgui.cvReleaseCapture(capture);
		return bufferedImage;
		} catch (Exception e) {
			// TODO: handle exception
			return bufferedImage;
		}
	}

	/**
     * 
     * 功能说明:将javacv的IplImage图像转为java 2d自身的BufferedImage
     * @param iplImage
     * javacv图像
     * @return BufferedImage
     * java 2d图像
     * @time:2016年8月3日下午12:03:05
     * @author:linghushaoxia
     * @exception:
     *
     */
    public static BufferedImage iplToBufferedImage(IplImage iplImage,int width,int height) {
	if (iplImage.width() > 0 && iplImage.height() > 0) {
	    BufferedImage image = new BufferedImage(iplImage.width(), iplImage.height(), BufferedImage.TYPE_3BYTE_BGR);
	    iplImage.copyTo(image);
	    
	    BufferedImage retimage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    
	    Graphics g = retimage.createGraphics();
	    g.drawImage(image, 0, 0,width,height, null);
	    g.dispose();

	    
	    return retimage;
	}
	return null;
    }    
    
    public static BufferedImage iplToBufferedImage(IplImage iplImage) {
	if (iplImage.width() > 0 && iplImage.height() > 0) {
	    BufferedImage image = new BufferedImage(iplImage.width(), iplImage.height(), BufferedImage.TYPE_3BYTE_BGR);
	    iplImage.copyTo(image);
	        
	    return image;
	}
	return null;
    }
    /**
     * 
     * 功能说明:将javacv的IplImage图像转为java 2d自身的BufferedImage
     * @param iplImage
     * javacv图像
     * @return BufferedImage
     * java 2d图像
     * @time:2016年8月3日下午12:24:50
     * @author:linghushaoxia
     * @exception:
     *
     */
    public static BufferedImage iplToBufImgData(IplImage mat,int width,int height) {
	if (width > 0 && height > 0) {
	    BufferedImage image = new BufferedImage(width, height,
		    BufferedImage.TYPE_3BYTE_BGR);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    BytePointer bytePointer =new BytePointer(data);
	   mat.imageData(bytePointer);
	    return image;
	}
	return null;
    }
    
    /**  
     * 把gif图片按帧拆分成jpg图片           
     * @param gifName String 小gif图片(路径+名称)  
     * @param path String 生成小jpg图片的路径  
     * @return String[] 返回生成小jpg图片的名称  
     */  
    public static List<BufferedImage> splitGif(InputStream is) {  
        try {          	
        	List<BufferedImage> bufferedImages=new ArrayList<BufferedImage>();
            GifDecoder decoder = new GifDecoder();             
            decoder.read(is);  
            int n = decoder.getFrameCount(); //得到frame的个数  
            for (int i = 0; i < n; i++) {  
                BufferedImage frame = decoder.getFrame(i); //得到帧     
                bufferedImages.add(frame);
            }  
            return bufferedImages;  
        } catch (Exception e) {  
            System.out.println( "splitGif Failed!");  
            e.printStackTrace();  
            return null;  
        }  
   }
}
