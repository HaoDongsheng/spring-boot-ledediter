package org.hds.Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sun.font.FontDesignMetrics;

public class DrawTextGraphics {
	// 图文变成图片
	public static BufferedImage getImagebyJsonArray(int playtype, JSONArray JarrContext, JSONObject jsonspecial,
			int itemwidth, int itemheight) {
		try {
			int width = 0, left = 0;
			List<JSONObject> JSONlist = new ArrayList<JSONObject>();
			if (JarrContext != null && JarrContext.size() > 0) {
				if (playtype == 1) {
					for (int r = 0; r < JarrContext.size(); r++) {// 多行数据
						JSONArray Jarrr = JarrContext.getJSONArray(r);
						int rowwidth = 0;
						left = width;
						for (int t = 0; t < Jarrr.size(); t++) {// 图文混排数据
							JSONObject jsonObject = Jarrr.getJSONObject(t);
							int itemType = jsonObject.getInteger("itemType");
							switch (itemType) {
							case 0: {
								boolean isIncode = false;
								if (jsonspecial != null || !isIncode) {
									String backcolor = jsonObject.getString("backColor");
									String forecolor = jsonObject.getString("foreColor");

									String fontName = jsonObject.getString("fontName");
									int fontSize = jsonObject.getIntValue("fontSize");
									String txtval = jsonObject.getString("value");
									Font font = new Font(fontName, Font.PLAIN, fontSize);
									FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);

									double sx = 1, sy = 1;
									if (jsonspecial != null) {
										JSONObject jsonscale = jsonspecial.getJSONObject("scale");
										if (jsonscale != null) {
											sx = jsonscale.getDoubleValue("scaleX") / 100;
											sy = jsonscale.getDoubleValue("scaleY") / 100;
										}
									}
									int txtwidth = 0;
									for (int i = 0; i < txtval.length(); i++) {
										txtwidth = metrics.charWidth(txtval.charAt(i));

										txtwidth = (int) (txtwidth * sx);

										int top = 0;
										int txth = fontSize;// metrics.getHeight();
										txth = (int) (txth * sy);
										if (itemheight > txth) {
											top = (itemheight - txth) / 2;
										}
										top = top + metrics.getAscent() - 2;

										JSONObject itemJsonObject = new JSONObject();
										itemJsonObject.put("type", 0);
										itemJsonObject.put("x", left);
										itemJsonObject.put("y", top);
										itemJsonObject.put("w", txtwidth);
										itemJsonObject.put("value", txtval.charAt(i));
										itemJsonObject.put("sx", sx);
										itemJsonObject.put("sy", sy);
										if (Character.isSpaceChar(txtval.charAt(i))) {
											itemJsonObject.put("fontName", "SimSun");
										} else {
											itemJsonObject.put("fontName", fontName);
										}
										// itemJsonObject.put("fontName", fontName);
										itemJsonObject.put("fontSize", fontSize);
										itemJsonObject.put("backColor", backcolor);
										itemJsonObject.put("foreColor", forecolor);

										JSONlist.add(itemJsonObject);
										left += txtwidth;
										rowwidth = left;
									}
								}
							}
								;
								break;
							case 2:
							case 3: {
								String urlString = jsonObject.getString("value");
								if (urlString.contains("base64")) {
									String base64ImgString = urlString.substring(urlString.indexOf("base64,") + 7);

									Base64.Decoder decoder = Base64.getDecoder();
									byte[] imgBytes = decoder.decode(base64ImgString);

									ByteArrayInputStream in = new ByteArrayInputStream(imgBytes); // 将b作为输入流；

									BufferedImage bufImage = ImageIO.read(in); // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();

									BufferedImage bufnewImage = DrawTextGraphics.getnewbufImage(bufImage, itemheight);

									JSONObject itemJsonObject = new JSONObject();
									itemJsonObject.put("type", 1);
									itemJsonObject.put("x", left);
									itemJsonObject.put("y", 0);
									itemJsonObject.put("w", bufnewImage.getWidth());
									itemJsonObject.put("value", bufnewImage);
									JSONlist.add(itemJsonObject);

									left += bufnewImage.getWidth();
									rowwidth += left;
								} else {
									URL url = new URL(urlString);
									BufferedImage bufImage = ImageIO.read(url);
									JSONObject itemJsonObject = new JSONObject();
									itemJsonObject.put("type", 1);
									itemJsonObject.put("x", left);
									itemJsonObject.put("y", 0);
									itemJsonObject.put("w", bufImage.getWidth());
									itemJsonObject.put("value", bufImage);
									JSONlist.add(itemJsonObject);
									left += bufImage.getWidth();
									rowwidth += left;
								}
							}
								;
								break;
							default:
								break;
							}
						}

						int rowCount = (int) Math.ceil((double) (rowwidth) / 8);
						width += rowCount * 8;
					}
				} else {
					for (int r = 0; r < JarrContext.size(); r++) {// 多行数据
						JSONArray Jarrr = JarrContext.getJSONArray(r);
						int rowwidth = 0;
						left = width;
						for (int t = 0; t < Jarrr.size(); t++) {// 图文混排数据
							JSONObject jsonObject = Jarrr.getJSONObject(t);
							int itemType = jsonObject.getInteger("itemType");
							switch (itemType) {
							case 0: {
								boolean isIncode = false;
								if (jsonspecial != null || !isIncode) {
									String backcolor = jsonObject.getString("backColor");
									String forecolor = jsonObject.getString("foreColor");

									String fontName = jsonObject.getString("fontName");
									int fontSize = jsonObject.getIntValue("fontSize");
									String txtval = jsonObject.getString("value");
									Font font = new Font(fontName, Font.PLAIN, fontSize);
									FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);

									double sx = 1, sy = 1;
									if (jsonspecial != null) {
										JSONObject jsonscale = jsonspecial.getJSONObject("scale");
										if (jsonscale != null) {
											sx = jsonscale.getDoubleValue("scaleX") / 100;
											sy = jsonscale.getDoubleValue("scaleY") / 100;
										}
									}

									int top = 0;
									int txth = fontSize;// metrics.getHeight();
									txth = (int) (txth * sy);
									if (itemheight > txth) {
										top = (itemheight - txth) / 2;
									}
									top = top + metrics.getAscent() - 2;

									for (int i = 0; i < txtval.length(); i++) {
										int charw = (int) (metrics.charWidth(txtval.charAt(i)) * sx);

										JSONObject itemJsonObject = new JSONObject();
										itemJsonObject.put("type", 0);
										itemJsonObject.put("y", top);
										itemJsonObject.put("value", txtval.charAt(i));
										itemJsonObject.put("sx", sx);
										itemJsonObject.put("sy", sy);
										if (Character.isSpaceChar(txtval.charAt(i))) {
											itemJsonObject.put("fontName", "SimSun");
										} else {
											itemJsonObject.put("fontName", fontName);
										}

										itemJsonObject.put("fontSize", fontSize);
										itemJsonObject.put("backColor", backcolor);
										itemJsonObject.put("foreColor", forecolor);
										itemJsonObject.put("w", charw);

										if (left / itemwidth != (left + charw - 1) / itemwidth) {
											if (JSONlist.size() > 0) {
												JSONObject jObject = JSONlist.get(JSONlist.size() - 1);
												if (jObject.getIntValue("type") == 0) {
													int px = jObject.getIntValue("x");
													JSONlist.get(JSONlist.size() - 1).put("w",
															(left + charw) / itemwidth * itemwidth - px);
												}
											}
											itemJsonObject.put("x", (left + charw) / itemwidth * itemwidth);
											left = (left + charw) / itemwidth * itemwidth + charw;
										} else {
											itemJsonObject.put("x", left);
											left += charw;
										}
										JSONlist.add(itemJsonObject);
										rowwidth = left;
									}
								}
							}
								;
								break;
							case 2:
							case 3: {
								String urlString = jsonObject.getString("value");
								if (urlString.contains("base64")) {
									String base64ImgString = urlString.substring(urlString.indexOf("base64,") + 7);

									Base64.Decoder decoder = Base64.getDecoder();
									byte[] imgBytes = decoder.decode(base64ImgString);

									ByteArrayInputStream in = new ByteArrayInputStream(imgBytes); // 将b作为输入流；

									BufferedImage bufImage = ImageIO.read(in); // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();

									BufferedImage bufnewImage = DrawTextGraphics.getnewbufImage(bufImage, itemheight);

									int imgWidth = bufnewImage.getWidth();

									JSONObject itemJsonObject = new JSONObject();
									itemJsonObject.put("type", 1);

									itemJsonObject.put("w", imgWidth);
									itemJsonObject.put("y", 0);
									itemJsonObject.put("value", bufnewImage);

									if (left % itemwidth != 0
											&& left / itemwidth != (left + imgWidth - 1) / itemwidth) {
										itemJsonObject.put("x", (left + imgWidth) / itemwidth * itemwidth);
										left = (left + imgWidth) / itemwidth * itemwidth + imgWidth;
									} else {
										itemJsonObject.put("x", left);
										left += imgWidth;
									}
									JSONlist.add(itemJsonObject);
									rowwidth = left;
								} else {
									URL url = new URL(urlString);
									BufferedImage bufImage = ImageIO.read(url);
									int imgWidth = bufImage.getWidth();
									JSONObject itemJsonObject = new JSONObject();
									itemJsonObject.put("type", 1);
									itemJsonObject.put("y", 0);
									itemJsonObject.put("w", imgWidth);
									itemJsonObject.put("value", bufImage);
									if (left % itemwidth != 0
											&& left / itemwidth != (left + imgWidth - 1) / itemwidth) {
										itemJsonObject.put("x", (left + imgWidth) / itemwidth * itemwidth);
										left = (left + imgWidth) / itemwidth * itemwidth + imgWidth;
									} else {
										itemJsonObject.put("x", left);
										left += imgWidth;
									}
									JSONlist.add(itemJsonObject);
									rowwidth = left;
								}
							}
								;
								break;
							default:
								break;
							}
						}

						int rowCount = (int) Math.ceil((double) (rowwidth) / (double) (itemwidth));
						width += rowCount * itemwidth;
					}
				}

				if (JSONlist.size() > 0) {
					BufferedImage bmp = new BufferedImage(width, itemheight, BufferedImage.TYPE_INT_BGR);
					Graphics2D graphics = bmp.createGraphics();// 画图

					for (int i = 0; i < JSONlist.size(); i++) {
						JSONObject jsonObject = JSONlist.get(i);
						int type = jsonObject.getIntValue("type");
						if (type == 0) {
							String backcolor = jsonObject.getString("backColor").trim();
							if (backcolor.indexOf("rgb") >= 0) {
								String[] rgb = backcolor.split(",");
								int r = Integer.parseInt(rgb[0].split("(")[1]);
								int g = Integer.parseInt(rgb[1]);
								int b = Integer.parseInt(rgb[2].split(")")[0]);
								StringBuilder builderr = new StringBuilder(Integer.toHexString(r & 0xff));
								while (builderr.length() < 2) {
									builderr.append("0");
								}

								StringBuilder builderg = new StringBuilder(Integer.toHexString(g & 0xff));
								while (builderg.length() < 2) {
									builderg.append("0");
								}
								StringBuilder builderb = new StringBuilder(Integer.toHexString(b & 0xff));
								while (builderb.length() < 2) {
									builderb.append("0");
								}
								backcolor = "#" + builderr + builderg + builderb;
							}
							String forecolor = jsonObject.getString("foreColor").trim();
							if (forecolor.indexOf("rgb") >= 0) {
								String[] rgb = forecolor.split(",");
								int r = Integer.parseInt(rgb[0].split("(")[1]);
								int g = Integer.parseInt(rgb[1]);
								int b = Integer.parseInt(rgb[2].split(")")[0]);

								StringBuilder builderr = new StringBuilder(Integer.toHexString(r & 0xff));
								while (builderr.length() < 2) {
									builderr.append("0");
								}

								StringBuilder builderg = new StringBuilder(Integer.toHexString(g & 0xff));
								while (builderg.length() < 2) {
									builderg.append("0");
								}

								StringBuilder builderb = new StringBuilder(Integer.toHexString(b & 0xff));
								while (builderb.length() < 2) {
									builderb.append("0");
								}
								forecolor = "#" + builderr + builderg + builderb;
							}
							String fontName = jsonObject.getString("fontName").trim();
							int fontSize = jsonObject.getIntValue("fontSize");

							Font font = new Font(fontName, Font.PLAIN, fontSize);

							int colorbbindex = Integer.parseInt(backcolor.substring(1), 16);
							Color m_backcolor = new Color(colorbbindex);

							if (backcolor != "#ffffff") {
								graphics.setColor(m_backcolor);// 在换成黑色
								graphics.fillRect(jsonObject.getIntValue("x"), 0, jsonObject.getIntValue("w"),
										itemheight);
							}
							String prints = jsonObject.getString("value");

							int colorbindex = Integer.parseInt(forecolor.substring(1), 16);
							Color m_forecolor = new Color(colorbindex);
							int x = jsonObject.getIntValue("x"), y = jsonObject.getIntValue("y");
							GradientPaint paint = null;
							if (jsonspecial != null) {
								JSONObject jsonsketch = jsonspecial.getJSONObject("sketch");
								JSONObject jsonshadow = jsonspecial.getJSONObject("shadow");
								JSONObject jsongradient = jsonspecial.getJSONObject("gradient");
								int strokeWidth = 0;
								if (jsonsketch != null) {
									Color m_sketchcolor = new Color(
											Integer.parseInt(jsonsketch.getString("strokeStyle").substring(1), 16));
									graphics.setColor(m_sketchcolor);// 描边颜色
									strokeWidth = jsonsketch.getIntValue("strokeWidth");
									for (int s = 0; s < strokeWidth; s++) {
										// 描边上下左右移动点画
										graphics.drawString(prints, x + s + 1, y);
										graphics.drawString(prints, x - s - 1, y);
										graphics.drawString(prints, x, y + s + 1);
										graphics.drawString(prints, x, y - s - 1);
									}
								}

								if (jsonshadow != null) {
									Color m_shadowcolor = new Color(
											Integer.parseInt(jsonshadow.getString("shadowColor").substring(1), 16));
									graphics.setColor(m_shadowcolor);// 阴影颜色
									int shadowBlur = jsonshadow.getIntValue("shadowBlur");
									for (int s = 0; s < shadowBlur; s++) {
										// 阴影左下移动点画
										graphics.drawString(prints, strokeWidth + x + s + 1, strokeWidth + y + s + 1);
									}
								}
								if (jsongradient != null) {
									String gradientdirection = jsongradient.getString("gradientdirection");
									Color gradientcolor1 = new Color(Integer
											.parseInt(jsongradient.getString("gradientcolor1").substring(1), 16));
									Color gradientcolor2 = new Color(Integer
											.parseInt(jsongradient.getString("gradientcolor2").substring(1), 16));
									switch (gradientdirection) {
									case "horizontal": {
										paint = new GradientPaint(0, 0, gradientcolor1, width, 0, gradientcolor2, true);
									}
										;
										break;
									case "vertical": {
										paint = new GradientPaint(0, 0, gradientcolor1, 0, itemheight, gradientcolor2,
												true);
									}
										;
										break;
									case "oblique": {
										paint = new GradientPaint(0, 0, gradientcolor1, width, itemheight,
												gradientcolor2, true);
									}
										;
										break;
									}
									graphics.setPaint(paint);// 设置渐变
								}
							}
							graphics.setFont(font);// 设置画笔字体

							if (paint == null) {
								graphics.setColor(m_forecolor);
							} // 在换成黑色
							AffineTransform affineTransform = new AffineTransform();
							affineTransform.scale(jsonObject.getIntValue("sx"), jsonObject.getIntValue("sy"));
							graphics.setTransform(affineTransform);
							graphics.drawString(prints, jsonObject.getIntValue("x"), jsonObject.getIntValue("y"));
						} else {
							BufferedImage img = (BufferedImage) jsonObject.get("value");
							graphics.drawImage(img, jsonObject.getIntValue("x"), jsonObject.getIntValue("y"),
									img.getWidth(), img.getHeight(), null);
						}

					}

					// ImageIO.write(bmp, "bmp", new File("E:/test.bmp"));
					// return null;
					return bmp;
				} else {
					return null;
				}

			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static BufferedImage getbufImage(String txtval, String fontName, int fontSize, String backcolor,
			String forecolor, int itemheight, JSONObject jsonspecial) {

		try {
			if (forecolor == "" || forecolor.equals("")) {
				forecolor = "#000000";
			}
			if (backcolor == "" || backcolor.equals("")) {
				backcolor = "#ffffff";
			}

			Font font = new Font(fontName, Font.PLAIN, fontSize);
			FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);

			double sx = 1, sy = 1;
			if (jsonspecial != null) {
				JSONObject jsonscale = jsonspecial.getJSONObject("scale");
				if (jsonscale != null) {
					sx = jsonscale.getDoubleValue("scaleX") / 100;
					sy = jsonscale.getDoubleValue("scaleY") / 100;
				}
			}
			int width = 0;
			for (int i = 0; i < txtval.length(); i++) {
				width += metrics.charWidth(txtval.charAt(i));
			}
			width = (int) (width * sx);
			BufferedImage bmp = new BufferedImage(width, itemheight, BufferedImage.TYPE_INT_BGR);

			Graphics2D graphics = bmp.createGraphics();// 画图

			int colorbbindex = Integer.parseInt(backcolor.substring(1), 16);
			Color m_backcolor = new Color(colorbbindex);

			if (backcolor != "#ffffff") {
				graphics.setColor(m_backcolor);// 在换成黑色
				graphics.fillRect(0, 0, width, itemheight);
			}
			String prints = txtval;

			int colorbindex = Integer.parseInt(forecolor.substring(1), 16);
			Color m_forecolor = new Color(colorbindex);

			graphics.setFont(font);// 设置画笔字体

			int top = 0;
			int txth = fontSize;// metrics.getHeight();
			txth = (int) (txth * sy);
			if (itemheight > txth) {
				top = (itemheight - txth) / 2;
			}
			int x = 0, y = top + metrics.getAscent() - 2;
			GradientPaint paint = null;
			if (jsonspecial != null) {
				JSONObject jsonsketch = jsonspecial.getJSONObject("sketch");
				JSONObject jsonshadow = jsonspecial.getJSONObject("shadow");
				JSONObject jsongradient = jsonspecial.getJSONObject("gradient");
				int strokeWidth = 0;
				if (jsonsketch != null) {
					Color m_sketchcolor = new Color(
							Integer.parseInt(jsonsketch.getString("strokeStyle").substring(1), 16));
					graphics.setColor(m_sketchcolor);// 描边颜色
					strokeWidth = jsonsketch.getIntValue("strokeWidth");
					for (int s = 0; s < strokeWidth; s++) {
						// 描边上下左右移动点画
						graphics.drawString(prints, x + s + 1, y);
						graphics.drawString(prints, x - s - 1, y);
						graphics.drawString(prints, x, y + s + 1);
						graphics.drawString(prints, x, y - s - 1);
					}
				}

				if (jsonshadow != null) {
					Color m_shadowcolor = new Color(
							Integer.parseInt(jsonshadow.getString("shadowColor").substring(1), 16));
					graphics.setColor(m_shadowcolor);// 阴影颜色
					int shadowBlur = jsonshadow.getIntValue("shadowBlur");
					for (int s = 0; s < shadowBlur; s++) {
						// 阴影左下移动点画
						graphics.drawString(prints, strokeWidth + x + s + 1, strokeWidth + y + s + 1);
					}
				}
				if (jsongradient != null) {
					String gradientdirection = jsongradient.getString("gradientdirection");
					Color gradientcolor1 = new Color(
							Integer.parseInt(jsongradient.getString("gradientcolor1").substring(1), 16));
					Color gradientcolor2 = new Color(
							Integer.parseInt(jsongradient.getString("gradientcolor2").substring(1), 16));
					switch (gradientdirection) {
					case "horizontal": {
						paint = new GradientPaint(0, 0, gradientcolor1, width, 0, gradientcolor2, true);
					}
						;
						break;
					case "vertical": {
						paint = new GradientPaint(0, 0, gradientcolor1, 0, itemheight, gradientcolor2, true);
					}
						;
						break;
					case "oblique": {
						paint = new GradientPaint(0, 0, gradientcolor1, width, itemheight, gradientcolor2, true);
					}
						;
						break;
					}
					graphics.setPaint(paint);// 设置渐变
				}
			}

			graphics.setFont(font);// 设置画笔字体
			if (paint == null) {
				graphics.setColor(m_forecolor);
			} // 在换成黑色
			AffineTransform affineTransform = new AffineTransform();
			affineTransform.scale(sx, sy);
			graphics.setTransform(affineTransform);
			graphics.drawString(prints, x, y);
			graphics.dispose();

			// ImageIO.write(bmp, "PNG", new File("E:/11.png"));

			return bmp;
		} catch (Exception e) {
			return null;
		}
	}

	public static BufferedImage getBigbufImage(List<BufferedImage> bImages, int itemH) {
		try {
			int width = 0, height = itemH;
			if (bImages != null && bImages.size() > 0) {
				for (int i = 0; i < bImages.size(); i++) {
					width += bImages.get(i).getWidth();
					if (bImages.get(i).getHeight() > height) {
						height = bImages.get(i).getHeight();
					}
				}

				BufferedImage bmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
				Graphics2D graphics = bmp.createGraphics();// 画图
				int left = 0;
				for (int i = 0; i < bImages.size(); i++) {
					BufferedImage img = bImages.get(i);
					BufferedImage bufnewImage = getnewbufImage(img, itemH);
					graphics.drawImage(bufnewImage, left, 0, bufnewImage.getWidth(), bufnewImage.getHeight(), null);
					left += img.getWidth();
				}

				graphics.dispose();
				// ImageIO.write(bmp, "png", new File("E:/big.png"));
				return bmp;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static BufferedImage getnewbufImage(BufferedImage bImage, int itemH) {
		try {

			if (bImage != null) {
				int height = bImage.getHeight();
				int width = bImage.getWidth();

				BufferedImage bmp = new BufferedImage(width, itemH, BufferedImage.TYPE_INT_BGR);
				Graphics2D graphics = bmp.createGraphics();// 画图

				if (height < itemH) {
					int top = (itemH - height) / 2;
					graphics.drawImage(bImage, 0, top, width, height, null);
				} else {
					graphics.drawImage(bImage, 0, 0, width, height, null);
				}

				graphics.dispose();
				return bmp;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static BufferedImage getnewbufImage(BufferedImage bImage, int w, int h) {
		try {

			if (bImage != null) {
				// int height = bImage.getHeight();
				// int width = bImage.getWidth();

				BufferedImage bmp = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D graphics = bmp.createGraphics();// 画图
				graphics.setColor(Color.white);
				graphics.setBackground(Color.white);
				graphics.drawImage(bImage, 0, 0, w, h, null);

				graphics.dispose();
				return bmp;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把多张jpg图片合成一张
	 * 
	 * @param pic    String[] 多个jpg文件名 包含路径
	 * @param newPic String 生成的gif文件名 包含路径
	 */
	private static void jpgToGif(String pic[], String newPic) {
		try {
			AnimatedGifEncoder e = new AnimatedGifEncoder(); // 请见本博客文章
			e.setRepeat(0);
			e.start(newPic);
			BufferedImage src[] = new BufferedImage[pic.length];
			for (int i = 0; i < src.length; i++) {
				e.setDelay(200); // 设置播放的延迟时间
				src[i] = ImageIO.read(new File(pic[i])); // 读入需要播放的jpg文件
				e.addFrame(src[i]); // 添加到帧中
			}
			e.finish();
		} catch (Exception e) {
			System.out.println("jpgToGif Failed:");
			e.printStackTrace();
		}
	}

	/**
	 * 把gif图片按帧拆分成jpg图片
	 * 
	 * @param gifName String 小gif图片(路径+名称)
	 * @param path    String 生成小jpg图片的路径
	 * @return String[] 返回生成小jpg图片的名称
	 */
	private static String[] splitGif(String gifName, String path) {
		try {
			GifDecoder decoder = new GifDecoder();
			decoder.read(gifName);
			int n = decoder.getFrameCount(); // 得到frame的个数
			String[] subPic = new String[n];
			for (int i = 0; i < n; i++) {
				BufferedImage frame = decoder.getFrame(i); // 得到帧
				int delay = decoder.getDelay(i); // 得到延迟时间
			}
			return subPic;
		} catch (Exception e) {
			System.out.println("splitGif Failed!");
			e.printStackTrace();
			return null;
		}
	}

	private static BufferedImage zoomBufferedImageByQuality(BufferedImage bufferedImage, float quality) {
		try {
			// 得到指定Format图片的writer
			Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");// 得到迭代器
			ImageWriter writer = (ImageWriter) iter.next(); // 得到writer

			// 得到指定writer的输出参数设置(ImageWriteParam)
			ImageWriteParam iwp = writer.getDefaultWriteParam();
			iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩
			iwp.setCompressionQuality(quality); // 设置压缩质量参数，0~1，1为最高质量
			iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
			ColorModel colorModel = ColorModel.getRGBdefault();
			// 指定压缩时使用的色彩模式
			iwp.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));
			// 开始打包图片，写入byte[]
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流
			IIOImage iIamge = new IIOImage(bufferedImage, null, null);
			// 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
			// 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
			writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
			writer.write(null, iIamge, iwp);

			// 获取压缩后的btye
			byte[] tempByte = byteArrayOutputStream.toByteArray();

			ByteArrayInputStream in = new ByteArrayInputStream(tempByte); // 将b作为输入流；
			BufferedImage bufImage = ImageIO.read(in);

			return bufImage;
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] Gif2Gif(byte[] imgBytes, int w, int h) {
		try {
			/*
			 * ByteArrayOutputStream os = new ByteArrayOutputStream(); ByteArrayInputStream
			 * in = new ByteArrayInputStream(imgBytes);
			 * 
			 * GifImage srcImage = com.gif4j.GifDecoder.decode(in);
			 * //主要调用的就是GifTransformer种的resize方法进行图片尺寸的调整 GifImage resizeImage =
			 * GifTransformer.resize(srcImage, w, h, false); GifEncoder.encode(resizeImage,
			 * os);
			 * 
			 * return os.toByteArray();
			 */
			/// *
			// OutputStream os = new FileOutputStream("E:/gg.gif"); //输出图片
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			AnimatedGifEncoder e = new AnimatedGifEncoder(); // 请见本博客文章
			e.setRepeat(0);
			e.start(os);

			ByteArrayInputStream in = new ByteArrayInputStream(imgBytes);
			GifDecoder decoder = new GifDecoder();

			decoder.read(in);
			byte[] ColorTab = decoder.getColorTab();
			e.setColorTab(ColorTab);
			int n = decoder.getFrameCount(); // 得到frame的个数

			for (int i = 0; i < n; i++) {
				BufferedImage frame = decoder.getFrame(i); // 得到帧

				int delay = decoder.getDelay(i); // 得到延迟时间

				// ImageIO.write(frame, "png", new File("E:/or/or"+i+".png"));
				BufferedImage newImage = getnewbufImage(frame, w, h);
				// ImageIO.write(newImage, "png", new File("E:/er/"+i+".png"));

				e.setDelay(delay); // 设置播放的延迟时间

				e.addFrame(newImage); // 添加到帧中
			}

			e.finish();

			return os.toByteArray();
			// return null;
			// */
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
	}

	public static JSONObject Gif2Img(String base64ImgString, int w, int h) {
		try {
			JSONObject jObject = new JSONObject();

			Base64.Decoder decoderBase64 = Base64.getDecoder();
			byte[] imgBytes = decoderBase64.decode(base64ImgString);
			ByteArrayInputStream in = new ByteArrayInputStream(imgBytes);
			GifDecoder decoder = new GifDecoder();

			decoder.read(in);
			int n = decoder.getFrameCount(); // 得到frame的个数

			int[] delayArr = new int[n];
			String[] imgArr = new String[n];
			for (int i = 0; i < n; i++) {
				BufferedImage frame = decoder.getFrame(i); // 得到帧
				int delay = decoder.getDelay(i); // 得到延迟时间
				delayArr[i] = delay;
				BufferedImage newImage = getnewbufImage(frame, w, h);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();// io流
				ImageIO.write(newImage, "png", baos);// 写入流中
				byte[] bytes = baos.toByteArray();// 转换成字
				Base64.Encoder encoderBase64 = Base64.getEncoder();
				String base64img = encoderBase64.encodeToString(bytes);
				imgArr[i] = base64img;
			}

			jObject.put("delayArr", delayArr);

			// ImageIO.write(bmp, "png", new File("E:/gif.png"));

			jObject.put("imgArr", imgArr);
			return jObject;
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
	}
}
