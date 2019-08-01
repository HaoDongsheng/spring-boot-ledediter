package org.hds.service.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.hds.NettyClient;
import org.hds.GJ_coding.GJ_ADitemcls;
import org.hds.GJ_coding.GJ_ADscriptcls;
import org.hds.GJ_coding.GJ_LoopADidlistcls;
import org.hds.GJ_coding.GJ_PictureAndTextcls;
import org.hds.GJ_coding.GJ_Playlistcls;
import org.hds.GJ_coding.GJ_Schedulingcls;
import org.hds.GJ_coding.GJ_Templatecls;
import org.hds.GJ_coding.GJ_Timequantumcls;
import org.hds.GJ_coding.GJ_codingCls;
import org.hds.GJ_coding.GJ_xieyiTypeEnum;
import org.hds.GJ_coding.GJ_xieyiVersionEnum;
import org.hds.Graphics.DrawTextGraphics;
import org.hds.Graphics.grabberVideoFramer;
import org.hds.mapper.advertisementMapper;
import org.hds.mapper.basemapMapper;
import org.hds.mapper.groupMapper;
import org.hds.mapper.infocodeMapper;
import org.hds.mapper.itemMapper;
import org.hds.mapper.playlistMapper;
import org.hds.mapper.playlistcodeMapper;
import org.hds.model.advertisement;
import org.hds.model.basemap;
import org.hds.model.group;
import org.hds.model.infocode;
import org.hds.model.item;
import org.hds.model.playlist;
import org.hds.model.playlistcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Service
public class getCode {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	groupMapper taxigroupMapper;
	@Autowired
	playlistMapper playlistMapper;
	@Autowired
	advertisementMapper advertisementMapper;
	@Autowired
	playlistcodeMapper playlistcodeMapper;
	@Autowired
	infocodeMapper infocodeMapper;
	@Autowired
	basemapMapper basemapMapper;
	@Autowired
	itemMapper itemMapper;

	// 生成发布id
	public int GetpubidbyGroupid(int groupid) {
		try {
			int MaxValue = 65535;
			int minValue = 100;
			int preID = 0, pubid = 0;
			preID = minValue;

			int getid = taxigroupMapper.selectpubidBygroupid(groupid);

			if (getid >= minValue) {
				preID = getid;
			}

			if (preID >= MaxValue) {
				preID = minValue;
			}

			for (int i = preID; i <= MaxValue; i++) {
				int pcount = advertisementMapper.selectCountBypubid(i, groupid);
				if (pcount == 0) {
					pubid = i;
					break;
				} else {

					i = advertisementMapper.selectmaxPubidBygroupid(groupid);
					// i+=1;
					if (i >= MaxValue) {
						i = minValue;
					}
				}
			}

			group tg = new group();
			tg.setGroupid(groupid);
			tg.setPubid(pubid + 1);
			taxigroupMapper.updatepubidBygroupid(tg);

			return pubid;
		} catch (Exception e) {
			return 0;
		}

	}

	// 广告编码
	public List<byte[]> GetCodeListbyid(JSONObject jsoninfo, JSONObject jsonarritem) {
		try {
			GJ_codingCls cls = new GJ_codingCls(2048);

			GJ_ADscriptcls adscript = new GJ_ADscriptcls();
			adscript.setM_adid(100);// 发布id
			adscript.setM_adtype((byte) 0);// 广告类型

			List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
			int basemapwidth = 0;
			int basemapheight = 0;
			JSONObject JsonBackgroundStyle = JSONObject.parseObject(jsoninfo.getString("BackgroundStyle"));
			if (JsonBackgroundStyle != null) {
				basemapwidth = JsonBackgroundStyle.getIntValue("width");
				basemapheight = JsonBackgroundStyle.getIntValue("height");

				JSONArray JArraybmStyle = JsonBackgroundStyle.getJSONArray("basemapStyle");
				if (JArraybmStyle != null && JArraybmStyle.size() > 0) {
					for (int i = 0; i < JArraybmStyle.size(); i++) {
						JSONObject jsonbmStyle = JArraybmStyle.getJSONObject(i);
						int basemalistmode = jsonbmStyle.getIntValue("basemalistmode");
						adscript.setM_basemalistmode((byte) basemalistmode);
						switch (basemalistmode) {
						case 2: {// 纯色图
							String basemabackcolor = jsonbmStyle.getString("basemabackcolor");
							BufferedImage image = new BufferedImage(basemapwidth, basemapheight,
									BufferedImage.TYPE_INT_RGB);
							Graphics graphics = image.getGraphics();
							graphics.setColor(new Color(Integer.parseInt(basemabackcolor.substring(1), 16)));
							graphics.fillRect(0, 0, basemapwidth, basemapheight);
							graphics.dispose();
							bufferedImages.add(image);
						}
							;
							break;
						case 3: {// 全彩图
							int basemalistsn = jsonbmStyle.getIntValue("basemalistsn");
							basemap basemap = basemapMapper.selectByPrimaryKey(basemalistsn);
							String base64ImgString = basemap.getBasemapdata();
							Base64.Decoder decoder = Base64.getDecoder();
							byte[] imgBytes = decoder.decode(base64ImgString);

							ByteArrayInputStream in = new ByteArrayInputStream(imgBytes); // 将b作为输入流；

							BufferedImage image = ImageIO.read(in); // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
							bufferedImages.add(image);
						}
							;
							break;
						}
					}
				}
			}
			adscript.setM_basemalist(bufferedImages);// 底图集合
			adscript.setM_basemapheight(basemapheight);// 底图高度
			adscript.setM_basemapwidth(basemapwidth);// 底图宽度
			// adscript.setM_crc(m_crc);//crc
			adscript.setM_framemode((byte) 0);// 边框样式
			adscript.setM_framespeed((byte) 0);// 边框速度
			adscript.setM_HDRmode((byte) 0);// 播放模式hdr
			adscript.setM_playtime(jsoninfo.getIntValue("playTimelength"));// 播放播放时长
			adscript.setM_transparency((byte) 0);// 透明度，对所有显示项有效

			List<GJ_ADitemcls> m_aditem = new ArrayList<GJ_ADitemcls>();
			for (String dataKey : jsonarritem.keySet()) {
				JSONArray jArrpage = jsonarritem.getJSONArray(dataKey);
				for (int i = 0; i < jArrpage.size(); i++) {
					JSONObject jitem = jArrpage.getJSONObject(i);

					byte m_type = (byte) (jitem.getInteger("type") & 0xff);
					int m_X1 = jitem.getInteger("left");
					int m_X2 = jitem.getInteger("left") + jitem.getInteger("width") - 1;
					int m_Y1 = jitem.getInteger("top");
					int m_Y2 = jitem.getInteger("top") + jitem.getInteger("height") - 1;

					byte m_fontno = (byte) (jitem.getInteger("fontno") & 0xff);

					JSONObject JsonItemStyle = JSONObject.parseObject(jitem.getString("itemstyle"));
					byte m_linkmove = (byte) (JsonItemStyle.getIntValue("linkmove") & 0xFF);
					byte m_looptime = (byte) (JsonItemStyle.getIntValue("looptime") & 0xFF);
					byte m_playspeed = (byte) ((JsonItemStyle.getIntValue("playspeed")) & 0xFF);
					byte m_playtype = (byte) (JsonItemStyle.getIntValue("playtype") & 0xFF);
					byte m_rollstop = (byte) (0 & 0xFF);// (byte)(JsonItemStyle.getIntValue("rollstop")& 0xFF);
					double m_stoptime = JsonItemStyle.getDoubleValue("stoptime");
					if (m_stoptime != 0) {
						m_rollstop = (byte) (1 & 0xFF);
					}
					byte m_gamma = (byte) (JsonItemStyle.getIntValue("gamma") & 0xFF);

					JSONObject jsonspecial = JsonItemStyle.getJSONObject("special");// 文字特效
					JSONArray JarrContext = JSONArray.parseArray(jitem.getString("contextJson"));
					GJ_ADitemcls aDitemcls = new GJ_ADitemcls();
					aDitemcls.setM_type(m_type);
					aDitemcls.setM_X1(m_X1);
					aDitemcls.setM_X2(m_X2);
					aDitemcls.setM_Y1(m_Y1);
					aDitemcls.setM_Y2(m_Y2);
					aDitemcls.setM_fontno(m_fontno);

					aDitemcls.setM_linkmove(m_linkmove);
					aDitemcls.setM_looptime(m_looptime);
					aDitemcls.setM_playspeed(m_playspeed);
					aDitemcls.setM_playtype(m_playtype);
					aDitemcls.setM_rollstop(m_rollstop);
					aDitemcls.setM_stoptime(m_stoptime);
					aDitemcls.setM_Gamma(m_gamma);

					List<GJ_PictureAndTextcls> m_texts = new ArrayList<GJ_PictureAndTextcls>();

					switch (m_type) {
					case 0/* 图文 */: {
						BufferedImage bufferedImage = DrawTextGraphics.getImagebyJsonArray(
								JsonItemStyle.getIntValue("playtype"), JarrContext, jsonspecial,
								jitem.getInteger("width"), jitem.getInteger("height"));

						if (bufferedImage != null) {
							GJ_PictureAndTextcls andTextcls = new GJ_PictureAndTextcls();
							andTextcls.setmTextType(1);
							andTextcls.setmPicture(bufferedImage);
							m_texts.add(andTextcls);
						} else {
							return null;
						}

						int Mandatorytime = JsonItemStyle.getIntValue("Mandatorytime");
						if (Mandatorytime == 0) {
							aDitemcls.setM_rolltimelenght(0);
							aDitemcls.setM_rollwidth(0);
						} else {
							int MandatorytimeLength = JsonItemStyle.getIntValue("MandatorytimeLength");
							if (m_playtype == 1) {
								aDitemcls.setM_rolltimelenght(MandatorytimeLength);
								aDitemcls.setM_rollwidth(bufferedImage.getWidth() + jitem.getInteger("width"));
							} else {
								int screenCount = bufferedImage.getWidth() / jitem.getInteger("width");
								aDitemcls.setM_stoptime((double) (MandatorytimeLength) / (double) (screenCount));
							}
						}
					}
						;
						break;
					case 3/* 视频 */: {
						aDitemcls.setM_type((byte) 3);
						if (JarrContext != null && JarrContext.size() > 0) {
							for (int r = 0; r < JarrContext.size(); r++) {
								JSONArray Jarrr = JarrContext.getJSONArray(r);
								for (int t = 0; t < Jarrr.size(); t++) {
									JSONObject jsonObject = Jarrr.getJSONObject(t);
									int itemType = jsonObject.getInteger("itemType");

									switch (itemType) {
									case 3: {
										try {
											double st = aDitemcls.getM_stoptime();
											if (st == 0) {
												st = 1;
											}
											JSONObject jsonalt = jsonObject.getJSONObject("alt");
											String fileName = jsonalt.getString("fileName");

											File path = new File(java.net.URLDecoder
													.decode(ResourceUtils.getURL("classpath:").getPath(), "utf-8"));
											// 如果上传目录为/static/images/upload/，则可以如下获取：
											File upload = new File(path.getAbsolutePath(), "static/upload_video/");

											List<BufferedImage> bImages = grabberVideoFramer.GetFramers(
													upload + File.separator + fileName, jitem.getInteger("width"),
													jitem.getInteger("height"), st);

											BufferedImage bufImage = DrawTextGraphics.getBigbufImage(bImages,
													jitem.getInteger("height"));
											GJ_PictureAndTextcls andTextcls = new GJ_PictureAndTextcls();
											andTextcls.setmTextType(itemType);
											andTextcls.setmPicture(bufImage);
											m_texts.add(andTextcls);
										} catch (Exception e) {
										}
									}
										;
										break;
									default:
										break;
									}
								}
							}
						}
					}
						;
						break;
					case 4/* gif动画 */: {
						aDitemcls.setM_type((byte) 4);
						if (JarrContext != null && JarrContext.size() > 0) {
							for (int r = 0; r < JarrContext.size(); r++) {
								JSONArray Jarrr = JarrContext.getJSONArray(r);
								for (int t = 0; t < Jarrr.size(); t++) {
									JSONObject jsonObject = Jarrr.getJSONObject(t);
									int itemType = jsonObject.getInteger("itemType");
									String urlString = jsonObject.getString("value");
									switch (itemType) {
									case 3: {
										try {
											String base64ImgString = urlString
													.substring(urlString.indexOf("base64,") + 7);

											int h = jitem.getInteger("height");
											int w = jitem.getInteger("width");

											Base64.Decoder decoder = Base64.getDecoder();
											byte[] imgBytes = decoder.decode(base64ImgString);

											if (w != jsonObject.getIntValue("width")
													|| h != jsonObject.getIntValue("height")) {
												imgBytes = DrawTextGraphics.Gif2Gif(imgBytes, w, h);
											}
											if (imgBytes == null) {
												return null;
											}
											aDitemcls.setM_GIFdata(imgBytes);
										} catch (Exception e) {
										}
									}
										;
										break;
									default:
										break;
									}
								}
							}
						}
					}
						;
						break;
					default: {
					}
						;
						break;
					}
					int mtCount = m_texts.size();
					if (mtCount > 50) {
						return null;
					}
					aDitemcls.setM_texts(m_texts);

					m_aditem.add(aDitemcls);
				}
			}

			adscript.setM_aditem(m_aditem);// 显示项

			List<byte[]> SendByteList = cls.GJ_AddAdScript(GJ_xieyiTypeEnum.GJ.getValue(),
					GJ_xieyiVersionEnum.one.getValue(), adscript);

			return SendByteList;
		} catch (Exception ex) {
			return null;
		}
	}

	// 广告编码
	public List<byte[]> GetCodeListbyid(advertisement adv) {
		try {
			GJ_codingCls cls = new GJ_codingCls(2048);

			GJ_ADscriptcls adscript = new GJ_ADscriptcls();
			adscript.setM_adid(100);// 发布id
			adscript.setM_adtype((byte) (Integer.parseInt(adv.getAdvtype()) & 0xff));// 广告类型

			List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
			int basemapwidth = 0;
			int basemapheight = 0;
			JSONObject JsonBackgroundStyle = JSONObject.parseObject(adv.getBackgroundstyle());
			if (JsonBackgroundStyle != null) {
				basemapwidth = JsonBackgroundStyle.getIntValue("width");
				basemapheight = JsonBackgroundStyle.getIntValue("height");

				JSONArray JArraybmStyle = JsonBackgroundStyle.getJSONArray("basemapStyle");
				if (JArraybmStyle != null && JArraybmStyle.size() > 0) {
					for (int i = 0; i < JArraybmStyle.size(); i++) {
						JSONObject jsonbmStyle = JArraybmStyle.getJSONObject(i);
						int basemalistmode = jsonbmStyle.getIntValue("basemalistmode");
						adscript.setM_basemalistmode((byte) basemalistmode);
						switch (basemalistmode) {
						case 2: {// 纯色图
							String basemabackcolor = jsonbmStyle.getString("basemabackcolor");
							BufferedImage image = new BufferedImage(basemapwidth, basemapheight,
									BufferedImage.TYPE_INT_RGB);
							Graphics graphics = image.getGraphics();
							graphics.setColor(new Color(Integer.parseInt(basemabackcolor.substring(1), 16)));
							graphics.fillRect(0, 0, basemapwidth, basemapheight);
							graphics.dispose();
							bufferedImages.add(image);
						}
							;
							break;
						case 3: {// 全彩图
							int basemalistsn = jsonbmStyle.getIntValue("basemalistsn");
							basemap basemap = basemapMapper.selectByPrimaryKey(basemalistsn);
							String base64ImgString = basemap.getBasemapdata();
							Base64.Decoder decoder = Base64.getDecoder();
							byte[] imgBytes = decoder.decode(base64ImgString);

							ByteArrayInputStream in = new ByteArrayInputStream(imgBytes); // 将b作为输入流；

							BufferedImage image = ImageIO.read(in); // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
							bufferedImages.add(image);
						}
							;
							break;
						}
					}
				}
			}
			adscript.setM_basemalist(bufferedImages);// 底图集合
			adscript.setM_basemapheight(basemapheight);// 底图高度
			adscript.setM_basemapwidth(basemapwidth);// 底图宽度
			// adscript.setM_crc(m_crc);//crc
			adscript.setM_framemode((byte) 0);// 边框样式
			adscript.setM_framespeed((byte) 0);// 边框速度
			adscript.setM_HDRmode((byte) (Integer.parseInt(adv.getPlaymode()) & 0xff));// 播放模式hdr
			adscript.setM_playtime(0);// 播放播放时长
			adscript.setM_transparency((byte) 0);// 透明度，对所有显示项有效

			List<GJ_ADitemcls> m_aditem = new ArrayList<GJ_ADitemcls>();
			logger.info("===读取显示项  广告id:" + adv.getinfoSN() + "===");
			List<item> itemlist = itemMapper.selectByadid(adv.getinfoSN());
			logger.info("===读取显示项  广告id:" + adv.getinfoSN() + "结果 " + itemlist.size() + "条记录===");
			if (itemlist != null && itemlist.size() > 0) {
				for (int i = 0; i < itemlist.size(); i++) {
					item item = itemlist.get(i);
					byte m_type = (byte) (item.getItemtype() & 0xff);
					int m_X1 = item.getItemleft();
					int m_X2 = item.getItemleft() + item.getItemwidth() - 1;
					int m_Y1 = item.getItemtop();
					int m_Y2 = item.getItemtop() + item.getItemheight() - 1;

					byte m_fontno = (byte) (item.getItemfontno() & 0xff);

					JSONObject JsonItemStyle = JSONObject.parseObject(item.getItemstyle());
					byte m_linkmove = (byte) (JsonItemStyle.getIntValue("linkmove") & 0xFF);
					byte m_looptime = (byte) (JsonItemStyle.getIntValue("looptime") & 0xFF);
					byte m_playspeed = (byte) ((JsonItemStyle.getIntValue("playspeed")) & 0xFF);
					byte m_playtype = (byte) (JsonItemStyle.getIntValue("playtype") & 0xFF);
					byte m_rollstop = (byte) (0 & 0xFF);// (byte)(JsonItemStyle.getIntValue("rollstop")& 0xFF);
					double m_stoptime = JsonItemStyle.getDoubleValue("stoptime");
					if (m_stoptime != 0) {
						m_rollstop = (byte) (1 & 0xFF);
					}
					byte m_gamma = (byte) (JsonItemStyle.getIntValue("gamma") & 0xFF);

					JSONObject jsonspecial = JsonItemStyle.getJSONObject("special");// 文字特效
					JSONArray JarrContext = JSONArray.parseArray(item.getItemcontextjson());
					GJ_ADitemcls aDitemcls = new GJ_ADitemcls();
					aDitemcls.setM_type(m_type);
					aDitemcls.setM_X1(m_X1);
					aDitemcls.setM_X2(m_X2);
					aDitemcls.setM_Y1(m_Y1);
					aDitemcls.setM_Y2(m_Y2);
					aDitemcls.setM_fontno(m_fontno);

					aDitemcls.setM_linkmove(m_linkmove);
					aDitemcls.setM_looptime(m_looptime);
					aDitemcls.setM_playspeed(m_playspeed);
					aDitemcls.setM_playtype(m_playtype);
					aDitemcls.setM_rollstop(m_rollstop);
					aDitemcls.setM_stoptime(m_stoptime);
					aDitemcls.setM_Gamma(m_gamma);

					List<GJ_PictureAndTextcls> m_texts = new ArrayList<GJ_PictureAndTextcls>();

					switch (m_type) {
					case 0/* 图文 */: {
						BufferedImage bufferedImage = DrawTextGraphics.getImagebyJsonArray(
								JsonItemStyle.getIntValue("playtype"), JarrContext, jsonspecial, item.getItemwidth(),
								item.getItemheight());
						if (bufferedImage != null) {
							GJ_PictureAndTextcls andTextcls = new GJ_PictureAndTextcls();
							andTextcls.setmTextType(1);
							andTextcls.setmPicture(bufferedImage);
							m_texts.add(andTextcls);
						} else {
							return null;
						}

						int Mandatorytime = JsonItemStyle.getIntValue("Mandatorytime");
						if (Mandatorytime == 0) {
							aDitemcls.setM_rolltimelenght(0);
							aDitemcls.setM_rollwidth(0);
						} else {
							int MandatorytimeLength = JsonItemStyle.getIntValue("MandatorytimeLength");
							if (m_playtype == 1) {
								aDitemcls.setM_rolltimelenght(MandatorytimeLength);
								aDitemcls.setM_rollwidth(bufferedImage.getWidth() + item.getItemwidth());
							} else {
								int screenCount = bufferedImage.getWidth() / item.getItemwidth();
								aDitemcls.setM_stoptime((double) (MandatorytimeLength) / (double) (screenCount));
							}
						}
					}
						;
						break;
					case 3/* 视频 */: {
						aDitemcls.setM_type((byte) 3);
						if (JarrContext != null && JarrContext.size() > 0) {
							for (int r = 0; r < JarrContext.size(); r++) {
								JSONArray Jarrr = JarrContext.getJSONArray(r);
								for (int t = 0; t < Jarrr.size(); t++) {
									JSONObject jsonObject = Jarrr.getJSONObject(t);
									int itemType = jsonObject.getInteger("itemType");

									switch (itemType) {
									case 3: {
										try {
											double st = aDitemcls.getM_stoptime();
											if (st == 0) {
												st = 1;
											}
											JSONObject jsonalt = jsonObject.getJSONObject("alt");
											String fileName = jsonalt.getString("fileName");

											File path = new File(java.net.URLDecoder
													.decode(ResourceUtils.getURL("classpath:").getPath(), "utf-8"));
											// 如果上传目录为/static/images/upload/，则可以如下获取：
											File upload = new File(path.getAbsolutePath(), "static/upload_video/");

											List<BufferedImage> bImages = grabberVideoFramer.GetFramers(
													upload + File.separator + fileName, item.getItemwidth(),
													item.getItemheight(), st);

											BufferedImage bufImage = DrawTextGraphics.getBigbufImage(bImages,
													item.getItemheight());
											GJ_PictureAndTextcls andTextcls = new GJ_PictureAndTextcls();
											andTextcls.setmTextType(itemType);
											andTextcls.setmPicture(bufImage);
											m_texts.add(andTextcls);
										} catch (Exception e) {
										}
									}
										;
										break;
									default:
										break;
									}
								}
							}
						}
					}
						;
						break;
					case 4/* gif动画 */: {
						aDitemcls.setM_type((byte) 4);
						if (JarrContext != null && JarrContext.size() > 0) {
							for (int r = 0; r < JarrContext.size(); r++) {
								JSONArray Jarrr = JarrContext.getJSONArray(r);
								for (int t = 0; t < Jarrr.size(); t++) {
									JSONObject jsonObject = Jarrr.getJSONObject(t);
									int itemType = jsonObject.getInteger("itemType");
									String urlString = jsonObject.getString("value");
									switch (itemType) {
									case 3: {
										try {
											String base64ImgString = urlString
													.substring(urlString.indexOf("base64,") + 7);

											int h = item.getItemheight();
											int w = item.getItemwidth();

											Base64.Decoder decoder = Base64.getDecoder();
											byte[] imgBytes = decoder.decode(base64ImgString);

											if (w != jsonObject.getIntValue("width")
													|| h != jsonObject.getIntValue("height")) {
												imgBytes = DrawTextGraphics.Gif2Gif(imgBytes, w, h);
											}
											if (imgBytes == null) {
												return null;
											}
											aDitemcls.setM_GIFdata(imgBytes);
										} catch (Exception e) {
										}
									}
										;
										break;
									default:
										break;
									}
								}
							}
						}
					}
						;
						break;
					default: {
					}
						;
						break;
					}
					int mtCount = m_texts.size();
					if (mtCount > 50) {
						return null;
					}
					aDitemcls.setM_texts(m_texts);

					m_aditem.add(aDitemcls);
				}
			} else {
				return null;
			}

			adscript.setM_aditem(m_aditem);// 显示项

			List<byte[]> SendByteList = cls.GJ_AddAdScript(GJ_xieyiTypeEnum.GJ.getValue(),
					GJ_xieyiVersionEnum.one.getValue(), adscript);

			return SendByteList;
		} catch (Exception ex) {
			return null;
		}
	}

	// 播放列表编码
	public List<byte[]> GetplaylistCodeListbyid(int adid) {
		try {
			GJ_codingCls cls = new GJ_codingCls(1024);

			GJ_Playlistcls gj_Playlistcls = new GJ_Playlistcls();
			gj_Playlistcls.setM_id(100);// 发布id
			gj_Playlistcls.setM_level((byte) 1);
			// 时间段设置

			List<GJ_Timequantumcls> timequantumList = new ArrayList<GJ_Timequantumcls>();

			GJ_Timequantumcls m_Timequantum = new GJ_Timequantumcls();
			m_Timequantum.setM_playstart("0");
			m_Timequantum.setM_playend("0");
			timequantumList.add(m_Timequantum);

			gj_Playlistcls.setM_Timequantum(timequantumList);

			GJ_Schedulingcls m_Scheduling = new GJ_Schedulingcls();

			m_Scheduling.setM_mode((byte) 0);
			List<GJ_LoopADidlistcls> m_Loop_adidlist = new ArrayList<GJ_LoopADidlistcls>();
			GJ_LoopADidlistcls aDidlistcls = new GJ_LoopADidlistcls();
			aDidlistcls.setM_ADid(adid);
			m_Loop_adidlist.add(aDidlistcls);
			m_Scheduling.setM_Loop_adidlist(m_Loop_adidlist);

			gj_Playlistcls.setM_Scheduling(m_Scheduling);

			List<byte[]> SendByteList = cls.GJ_AddPlayList(GJ_xieyiTypeEnum.GJ.getValue(),
					GJ_xieyiVersionEnum.one.getValue(), gj_Playlistcls);

			return SendByteList;
		} catch (Exception e) {
			return null;
		}
	}

	// 播放列表编码
	public List<byte[]> GetplaylistCodeListbyid(int level, int ScheduleType, int pubid, String quantums,
			String programlist) {
		try {
			JSONObject PrlJsonObject = null;
			if (ScheduleType == 2) {
				PrlJsonObject = JSONObject.parseObject(programlist);
			}

			GJ_codingCls cls = new GJ_codingCls(1024);

			GJ_Playlistcls gj_Playlistcls = new GJ_Playlistcls();
			gj_Playlistcls.setM_id(pubid);// 发布id
			gj_Playlistcls.setM_level((byte) (int) level);
			String lifeAct = "";// playlist.getString("lifeAct");
			String lifeDie = "";// playlist.getString("lifeDie");
			if (ScheduleType == 2) {
				lifeAct = PrlJsonObject.getString("lifeAct");
				lifeDie = PrlJsonObject.getString("lifeDie");
			}
			if (!lifeAct.equals("1999-09-09")) {
				gj_Playlistcls.setM_lifestart(lifeAct);
			}
			if (!lifeDie.equals("2100-09-09")) {
				gj_Playlistcls.setM_lifeend(lifeDie);
			}

			// 时间段设置
			// {"timelist":[{"lifeAct":"0","lifeDie":"0"}],"listcycle":"300"}
			JSONObject jsonTimequantum = JSONObject.parseObject(quantums);
			if (jsonTimequantum != null) {
				List<GJ_Timequantumcls> timequantumList = new ArrayList<GJ_Timequantumcls>();
				for (int i = 0; i < jsonTimequantum.getJSONArray("timelist").size(); i++) {
					JSONObject jsonObject = jsonTimequantum.getJSONArray("timelist").getJSONObject(i);
					GJ_Timequantumcls m_Timequantum = new GJ_Timequantumcls();
					m_Timequantum.setM_playstart(jsonObject.getString("lifeAct"));
					m_Timequantum.setM_playend(jsonObject.getString("lifeDie"));
					timequantumList.add(m_Timequantum);
				}
				gj_Playlistcls.setM_Timequantum(timequantumList);
			}

			GJ_Schedulingcls m_Scheduling = new GJ_Schedulingcls();
			// 设置排期
			m_Scheduling.setM_mode((byte) 1);

			gj_Playlistcls.setM_Scheduling(m_Scheduling);

			if (ScheduleType == 2) {
				m_Scheduling.setM_Template_cycle(PrlJsonObject.getIntValue("totalTimeLength"));
			} else {
				m_Scheduling.setM_Template_cycle(jsonTimequantum.getIntValue("listcycle"));
			}
			JSONArray jArrayLoop = null;
			if (ScheduleType == 2) {
				jArrayLoop = PrlJsonObject.getJSONArray("advlist");
			} else {
				jArrayLoop = JSONArray.parseArray(programlist);
			}

			if (jArrayLoop != null && jArrayLoop.size() > 0) {
				List<GJ_Templatecls> m_Template_adlist = new ArrayList<GJ_Templatecls>();
				for (int i = 0; i < jArrayLoop.size(); i++) {
					JSONObject jTemJsonObject = jArrayLoop.getJSONObject(i);
					int m_ADid = jTemJsonObject.getIntValue("infoid");
					advertisement advertisement = advertisementMapper.selectByPrimaryKey(m_ADid);
					int pid = advertisement.getPubid();

					GJ_Templatecls gj_Templatecls = new GJ_Templatecls();
					// 获取广告生命周期
					String m_ADidlifestart = advertisement.getlifeAct();
					String m_ADidlifeend = advertisement.getlifeDie();

					gj_Templatecls.setM_adid(advertisement.getPubid());
					gj_Templatecls.setM_adplaytime(jTemJsonObject.getInteger("timelenght"));
					gj_Templatecls.setM_ADlifestart(m_ADidlifestart);
					gj_Templatecls.setM_ADlifeend(m_ADidlifeend);

					JSONArray jArraytimeoffset = jTemJsonObject.getJSONArray("offsetlist");
					if (jArraytimeoffset != null && jArraytimeoffset.size() > 0) {
						List<String> m_timeoffset = new ArrayList<String>();
						for (int j = 0; j < jArraytimeoffset.size(); j++) {
							m_timeoffset.add(jArraytimeoffset.getString(j));
						}
						gj_Templatecls.setM_timeoffset(m_timeoffset);
					}
					m_Template_adlist.add(gj_Templatecls);
				}
				m_Scheduling.setM_Template_adlist(m_Template_adlist);
			}

			List<byte[]> SendByteList = cls.GJ_AddPlayList(GJ_xieyiTypeEnum.GJ.getValue(),
					GJ_xieyiVersionEnum.one.getValue(), gj_Playlistcls);

			return SendByteList;
		} catch (Exception e) {
			return null;
		}
	}

	// 广告编码
	public int GetCodeListbyid(advertisement adv, int pubid) {
		try {
			int MaxPackLength = taxigroupMapper.selectByPrimaryKey(adv.getGroupid()).getMaxPackLength();
			GJ_codingCls cls = new GJ_codingCls(MaxPackLength);

			GJ_ADscriptcls adscript = new GJ_ADscriptcls();
			adscript.setM_adid(pubid);// 发布id
			adscript.setM_adtype((byte) (Integer.parseInt(adv.getAdvtype()) & 0xff));// 广告类型

			List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
			int basemapwidth = 0;
			int basemapheight = 0;
			JSONObject JsonBackgroundStyle = JSONObject.parseObject(adv.getBackgroundstyle());
			if (JsonBackgroundStyle != null) {
				basemapwidth = JsonBackgroundStyle.getIntValue("width");
				basemapheight = JsonBackgroundStyle.getIntValue("height");

				JSONArray JArraybmStyle = JsonBackgroundStyle.getJSONArray("basemapStyle");
				if (JArraybmStyle != null && JArraybmStyle.size() > 0) {
					for (int i = 0; i < JArraybmStyle.size(); i++) {
						JSONObject jsonbmStyle = JArraybmStyle.getJSONObject(i);
						int basemalistmode = jsonbmStyle.getIntValue("basemalistmode");
						adscript.setM_basemalistmode((byte) basemalistmode);
						switch (basemalistmode) {
						case 2: {// 纯色图
							String basemabackcolor = jsonbmStyle.getString("basemabackcolor");
							BufferedImage image = new BufferedImage(basemapwidth, basemapheight,
									BufferedImage.TYPE_INT_RGB);
							Graphics graphics = image.getGraphics();
							graphics.setColor(new Color(Integer.parseInt(basemabackcolor.substring(1), 16)));
							graphics.fillRect(0, 0, basemapwidth, basemapheight);
							graphics.dispose();
							bufferedImages.add(image);
						}
							;
							break;
						case 3: {// 全彩图
							int basemalistsn = jsonbmStyle.getIntValue("basemalistsn");
							basemap basemap = basemapMapper.selectByPrimaryKey(basemalistsn);
							String base64ImgString = basemap.getBasemapdata();
							Base64.Decoder decoder = Base64.getDecoder();
							byte[] imgBytes = decoder.decode(base64ImgString);

							ByteArrayInputStream in = new ByteArrayInputStream(imgBytes); // 将b作为输入流；

							BufferedImage image = ImageIO.read(in); // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
							bufferedImages.add(image);
						}
							;
							break;
						}
					}
				}
			}
			adscript.setM_basemalist(bufferedImages);// 底图集合
			adscript.setM_basemapheight(basemapheight);// 底图高度
			adscript.setM_basemapwidth(basemapwidth);// 底图宽度
			// adscript.setM_crc(m_crc);//crc
			adscript.setM_framemode((byte) 0);// 边框样式
			adscript.setM_framespeed((byte) 0);// 边框速度
			adscript.setM_HDRmode((byte) (Integer.parseInt(adv.getPlaymode()) & 0xff));// 播放模式hdr
			adscript.setM_playtime(0);// 播放播放时长
			adscript.setM_transparency((byte) 0);// 透明度，对所有显示项有效

			List<GJ_ADitemcls> m_aditem = new ArrayList<GJ_ADitemcls>();
			logger.info("===读取显示项  广告id:" + adv.getinfoSN() + "===");
			List<item> itemlist = itemMapper.selectByadid(adv.getinfoSN());
			logger.info("===读取显示项  广告id:" + adv.getinfoSN() + "结果 " + itemlist.size() + "条记录===");
			if (itemlist != null && itemlist.size() > 0) {
				for (int i = 0; i < itemlist.size(); i++) {
					item item = itemlist.get(i);
					byte m_type = (byte) (item.getItemtype() & 0xff);
					int m_X1 = item.getItemleft();
					int m_X2 = item.getItemleft() + item.getItemwidth() - 1;
					int m_Y1 = item.getItemtop();
					int m_Y2 = item.getItemtop() + item.getItemheight() - 1;

					byte m_fontno = (byte) (item.getItemfontno() & 0xff);
					/*
					 * 暂时没内码全当图片，所有显示项前景色背景色透明度不给值 String Itembackcolor=item.getItembackcolor();
					 * Color m_fontbackcolor=null; if(Itembackcolor!=null && Itembackcolor!="") {
					 * int colorbindex = Integer.parseInt(Itembackcolor.substring(1),16);
					 * m_fontbackcolor=new Color(colorbindex); }
					 * 
					 * byte m_fontbacktransparency=(byte)(item.getItembackopacity() & 0xff);
					 * 
					 * String Itemforecolor=item.getItemforecolor(); Color m_fontforecolor=null;
					 * if(Itemforecolor!=null && Itemforecolor!="") { int colorfindex =
					 * Integer.parseInt(Itemforecolor.substring(1),16); m_fontforecolor=new
					 * Color(colorfindex); } byte
					 * m_fontforetransparency=(byte)(item.getItemforeopacity() & 0xff);;
					 */
					JSONObject JsonItemStyle = JSONObject.parseObject(item.getItemstyle());
					byte m_linkmove = (byte) (JsonItemStyle.getIntValue("linkmove") & 0xFF);
					byte m_looptime = (byte) (JsonItemStyle.getIntValue("looptime") & 0xFF);
					byte m_playspeed = (byte) ((JsonItemStyle.getIntValue("playspeed")) & 0xFF);
					byte m_playtype = (byte) (JsonItemStyle.getIntValue("playtype") & 0xFF);
					byte m_rollstop = (byte) (0 & 0xFF);// (byte)(JsonItemStyle.getIntValue("rollstop")& 0xFF);
					double m_stoptime = JsonItemStyle.getDoubleValue("stoptime");
					if (m_stoptime != 0) {
						m_rollstop = (byte) (1 & 0xFF);
					}
					byte m_gamma = (byte) (JsonItemStyle.getIntValue("gamma") & 0xFF);

					JSONObject jsonspecial = JsonItemStyle.getJSONObject("special");// 文字特效
					JSONArray JarrContext = JSONArray.parseArray(item.getItemcontextjson());
					GJ_ADitemcls aDitemcls = new GJ_ADitemcls();
					aDitemcls.setM_type(m_type);
					aDitemcls.setM_X1(m_X1);
					aDitemcls.setM_X2(m_X2);
					aDitemcls.setM_Y1(m_Y1);
					aDitemcls.setM_Y2(m_Y2);
					aDitemcls.setM_fontno(m_fontno);
					/*
					 * 暂时没内码全当图片，所有显示项前景色背景色透明度不给值
					 * 
					 * aDitemcls.setM_fontbackcolor(m_fontbackcolor);
					 * aDitemcls.setM_fontbacktransparency(m_fontbacktransparency);
					 * aDitemcls.setM_fontforecolor(m_fontforecolor);
					 * aDitemcls.setM_fontforetransparency(m_fontforetransparency);
					 */
					aDitemcls.setM_linkmove(m_linkmove);
					aDitemcls.setM_looptime(m_looptime);
					aDitemcls.setM_playspeed(m_playspeed);
					aDitemcls.setM_playtype(m_playtype);
					aDitemcls.setM_rollstop(m_rollstop);
					aDitemcls.setM_stoptime(m_stoptime);
					aDitemcls.setM_Gamma(m_gamma);

					List<GJ_PictureAndTextcls> m_texts = new ArrayList<GJ_PictureAndTextcls>();

					switch (m_type) {
					case 0/* 图文 */: {
						BufferedImage bufferedImage = DrawTextGraphics.getImagebyJsonArray(
								JsonItemStyle.getIntValue("playtype"), JarrContext, jsonspecial, item.getItemwidth(),
								item.getItemheight());
						// BufferedImage bufferedImage =
						// getImagebyJsonArray(JarrContext,jsonspecial,item.getItemheight());
						if (bufferedImage != null) {
							GJ_PictureAndTextcls andTextcls = new GJ_PictureAndTextcls();
							andTextcls.setmTextType(1);
							andTextcls.setmPicture(bufferedImage);
							m_texts.add(andTextcls);
						} else {
							return 2;
						}

						int Mandatorytime = JsonItemStyle.getIntValue("Mandatorytime");
						if (Mandatorytime == 0) {
							aDitemcls.setM_rolltimelenght(0);
							aDitemcls.setM_rollwidth(0);
						} else {
							int MandatorytimeLength = JsonItemStyle.getIntValue("MandatorytimeLength");
							if (m_playtype == 1) {
								aDitemcls.setM_rolltimelenght(MandatorytimeLength);
								aDitemcls.setM_rollwidth(bufferedImage.getWidth() + item.getItemwidth());
							} else {
								int screenCount = bufferedImage.getWidth() / item.getItemwidth();
								aDitemcls.setM_stoptime((double) (MandatorytimeLength) / (double) (screenCount));
							}
						}

					}
						;
						break;
					case 3/* 视频 */: {
						aDitemcls.setM_type((byte) 3);
						if (JarrContext != null && JarrContext.size() > 0) {
							for (int r = 0; r < JarrContext.size(); r++) {
								JSONArray Jarrr = JarrContext.getJSONArray(r);
								for (int t = 0; t < Jarrr.size(); t++) {
									JSONObject jsonObject = Jarrr.getJSONObject(t);
									int itemType = jsonObject.getInteger("itemType");

									switch (itemType) {
									case 3: {
										try {
											double st = aDitemcls.getM_stoptime();
											if (st == 0) {
												st = 1;
											}
											JSONObject jsonalt = jsonObject.getJSONObject("alt");
											String fileName = jsonalt.getString("fileName");

											File path = new File(java.net.URLDecoder
													.decode(ResourceUtils.getURL("classpath:").getPath(), "utf-8"));
											// 如果上传目录为/static/images/upload/，则可以如下获取：
											File upload = new File(path.getAbsolutePath(), "static/upload_video/");

											// List<BufferedImage> bimgs = grabberVideoFramer.GetFramers(upload +
											// File.separator +
											// fileName,jsonObject.getIntValue("width"),jsonObject.getIntValue("height"),st);
											List<BufferedImage> bImages = grabberVideoFramer.GetFramers(
													upload + File.separator + fileName, item.getItemwidth(),
													item.getItemheight(), st);
											/*
											 * for(int b=0;b<bimgs.size();b++) { GJ_PictureAndTextcls andTextcls=new
											 * GJ_PictureAndTextcls(); andTextcls.setmTextType(itemType);
											 * andTextcls.setmPicture(bimgs.get(b)); m_texts.add(andTextcls); }
											 */
											BufferedImage bufImage = DrawTextGraphics.getBigbufImage(bImages,
													item.getItemheight());
											GJ_PictureAndTextcls andTextcls = new GJ_PictureAndTextcls();
											andTextcls.setmTextType(itemType);
											andTextcls.setmPicture(bufImage);
											m_texts.add(andTextcls);
										} catch (Exception e) {
										}
									}
										;
										break;
									default:
										break;
									}
								}
							}
						}
					}
						;
						break;
					case 4/* gif动画 */: {
						aDitemcls.setM_type((byte) 4);
						if (JarrContext != null && JarrContext.size() > 0) {
							for (int r = 0; r < JarrContext.size(); r++) {
								JSONArray Jarrr = JarrContext.getJSONArray(r);
								for (int t = 0; t < Jarrr.size(); t++) {
									JSONObject jsonObject = Jarrr.getJSONObject(t);
									int itemType = jsonObject.getInteger("itemType");
									String urlString = jsonObject.getString("value");
									switch (itemType) {
									case 3: {
										try {
											String base64ImgString = urlString
													.substring(urlString.indexOf("base64,") + 7);

											int h = item.getItemheight();
											int w = item.getItemwidth();

											Base64.Decoder decoder = Base64.getDecoder();
											byte[] imgBytes = decoder.decode(base64ImgString);

											if (w != jsonObject.getIntValue("width")
													|| h != jsonObject.getIntValue("height")) {
												imgBytes = DrawTextGraphics.Gif2Gif(imgBytes, w, h);
											}
											if (imgBytes == null) {
												return 2;
											}
											aDitemcls.setM_GIFdata(imgBytes);
										} catch (Exception e) {
										}
									}
										;
										break;
									default:
										break;
									}
								}
							}
						}
					}
						;
						break;
					default: {
					}
						;
						break;
					}
					int mtCount = m_texts.size();
					if (mtCount > 50) {
						return 3;
					}
					aDitemcls.setM_texts(m_texts);

					m_aditem.add(aDitemcls);
				}
				logger.info("===读取显示项  广告id:" + adv.getinfoSN() + "结果 " + itemlist.size() + "条记录  添加显示项完成===");
			} else {
				logger.info("===读取显示项  广告id:" + adv.getinfoSN() + "结果 " + itemlist.size() + "条记录  返回值2===");
				return 2;
			}

			adscript.setM_aditem(m_aditem);// 显示项

			List<byte[]> SendByteList = cls.GJ_AddAdScript(GJ_xieyiTypeEnum.GJ.getValue(),
					GJ_xieyiVersionEnum.one.getValue(), adscript);
			int returnCrc = adscript.getM_crc();
			logger.info("===读取显示项  广告id:" + adv.getinfoSN() + "编码结束 ===");
			if (SendByteList != null && SendByteList.size() > 0) {
				infocodeMapper.deleteByinfoSN(adv.getinfoSN());
				JSONArray jsonArray = new JSONArray();
				int PackLength = 0;
				for (int i = 0; i < SendByteList.size(); i++) {
					byte[] bytes = SendByteList.get(i);
					PackLength += bytes.length;
					StringBuilder buf = new StringBuilder(bytes.length * 2);
					for (byte b : bytes) { // 使用String的format方法进行转换
						buf.append(String.format("%02x", new Integer(b & 0xff)));
					}
					jsonArray.add(buf.toString().trim());
				}

				if (PackLength > 250 * 1024) {
					return 8;
				}
				infocode record = new infocode();
				record.setinfoSN(adv.getinfoSN());
				record.setGroupid(adv.getGroupid());
				record.setPubid(pubid);
				record.setCodecontext(jsonArray.toJSONString());
				record.setCodecrc(returnCrc);
				record.setPackCount(SendByteList.size());
				record.setPackLength(PackLength);
				infocodeMapper.insert(record);
			} else {
				return 1;
			}

			return 0;
		} catch (Exception ex) {
			return 1;
		}
	}
	// 图文变成图片
	/*
	 * private BufferedImage getImagebyJsonArray(JSONArray JarrContext,JSONObject
	 * jsonspecial,int height) { try { int width = 0; List<BufferedImage>
	 * imagelist=new ArrayList<BufferedImage>(); if(JarrContext!=null &&
	 * JarrContext.size()>0) { for(int r=0;r<JarrContext.size();r++) {//多行数据
	 * JSONArray Jarrr = JarrContext.getJSONArray(r); for(int
	 * t=0;t<Jarrr.size();t++) {//图文混排数据 JSONObject
	 * jsonObject=Jarrr.getJSONObject(t); int itemType =
	 * jsonObject.getInteger("itemType"); switch (itemType) { case 0: { boolean
	 * isIncode=false; if(jsonspecial!=null || !isIncode) { String
	 * backcolor=jsonObject.getString("backColor"); String
	 * forecolor=jsonObject.getString("foreColor");
	 * 
	 * BufferedImage bufImage =
	 * DrawTextGraphics.getbufImage(jsonObject.getString("value"),jsonObject.
	 * getString("fontName"),jsonObject.getIntValue("fontSize"),backcolor,forecolor,
	 * height,jsonspecial);
	 * 
	 * imagelist.add(bufImage); width += bufImage.getWidth();
	 * //ImageIO.write(bufImage, "PNG", new File("E:/"+t+".png")); } }; break; case
	 * 2: case 3: { String urlString=jsonObject.getString("value");
	 * if(urlString.contains("base64")) { String base64ImgString =
	 * urlString.substring(urlString.indexOf("base64,") + 7);
	 * 
	 * Base64.Decoder decoder = Base64.getDecoder(); byte[] imgBytes =
	 * decoder.decode(base64ImgString);
	 * 
	 * ByteArrayInputStream in = new ByteArrayInputStream(imgBytes); //将b作为输入流；
	 * 
	 * BufferedImage bufImage = ImageIO.read(in);
	 * //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
	 * 
	 * BufferedImage bufnewImage = DrawTextGraphics.getnewbufImage(bufImage,height);
	 * 
	 * imagelist.add(bufnewImage);
	 * 
	 * width += bufnewImage.getWidth(); //ImageIO.write(bufnewImage, "PNG", new
	 * File("E:/"+t+".png")); } else { URL url=new URL(urlString); BufferedImage
	 * bufImage = ImageIO.read(url); imagelist.add(bufImage); width +=
	 * bufImage.getWidth(); } }; break; default: break; } } }
	 * 
	 * if(imagelist.size()>0) {
	 * 
	 * BufferedImage bmp=new BufferedImage(width, height,
	 * BufferedImage.TYPE_INT_BGR); Graphics2D graphics = bmp.createGraphics();//画图
	 * int left=0; for(int i=0;i<imagelist.size();i++) { BufferedImage img =
	 * imagelist.get(i); int top= (height - img.getHeight())/2;
	 * graphics.drawImage(img, left, top, img.getWidth(), img.getHeight(),null);
	 * left+=img.getWidth(); }
	 * 
	 * //ImageIO.write(bmp, "PNG", new File("e:/aa.png"));
	 * 
	 * return bmp; } else { return null; } } else { return null; } } catch
	 * (Exception e) { return null; } }
	 */

	// 生成发布id
	public int GetplpubidbyGroupid(int groupid) {
		try {
			int MaxValue = 65535;
			int minValue = 100;
			int preID = 0, pubid = 0;
			preID = minValue;
			int getid = taxigroupMapper.selectplpubidBygroupid(groupid);

			if (getid >= minValue) {
				preID = getid;
			}

			if (preID >= MaxValue) {
				preID = minValue;
			}

			List<Integer> pids = new ArrayList<Integer>();
			int maxpid = 0;
			List<playlist> playlists = playlistMapper.selectbygroupid(groupid);
			for (int i = 0; i < playlists.size(); i++) {
				String pubidString = playlists.get(i).getPubid();
				if (pubidString.contains(",")) {
					List<String> strsToList = Arrays.asList(pubidString.trim().split(","));
					for (int j = 0; j < strsToList.size(); j++) {
						int pid = Integer.parseInt(strsToList.get(j).trim());
						if (maxpid < pid) {
							pid = maxpid;
						}
						if (!pids.contains(pid)) {
							pids.add(pid);
						}
					}
				} else {
					int pid = Integer.parseInt(pubidString);
					if (maxpid < pid) {
						pid = maxpid;
					}
					if (!pids.contains(pid)) {
						pids.add(pid);
					}
				}
			}

			for (int i = preID; i <= MaxValue; i++) {
				// int pcount = 0;// playlistMapper.selectCountBypubid(i,groupid);
				if (!pids.contains(i)) {
					pubid = i;
					break;
				} else {

					i = maxpid;// playlistMapper.selectmaxPubidBygroupid(groupid);
					// i+=1;
					if (i >= MaxValue) {
						i = minValue;
					}
				}
			}

			group tg = new group();
			tg.setGroupid(groupid);
			tg.setplpubid(pubid + 1);
			taxigroupMapper.updateplpubidBygroupid(tg);
			// taxigroupMapper.updatepubidBygroupid(groupid,pubid);

			return pubid;
		} catch (Exception e) {
			return 0;
		}

	}

	// 获取多生命周期时段数据
	public JSONArray GetJsonArrayinfolist(JSONArray jArrayLoop) {
		try {
			JSONArray arrayjs = new JSONArray();

			if (jArrayLoop != null && jArrayLoop.size() > 0) {
				// 获取日期集合
				List<String> datelist = new ArrayList<String>();
				for (int i = 0; i < jArrayLoop.size(); i++) {
					JSONObject jTemJsonObject = jArrayLoop.getJSONObject(i);
					int m_ADid = jTemJsonObject.getIntValue("infosn");
					int timelenght = jTemJsonObject.getIntValue("timelenght");
					String lifeAct = jTemJsonObject.getString("lifeAct");
					String lifeDie = jTemJsonObject.getString("lifeDie");

					if (lifeAct.equals("")) {
						lifeAct = "1999-09-09";
					}
					if (lifeDie.equals("")) {
						lifeDie = "2100-09-09";
					}

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 加上时间
					// 必须捕获异常
					try {
						java.util.Date date = simpleDateFormat.parse(lifeDie);
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(date);
						calendar.add(Calendar.DATE, 1);
						date = calendar.getTime();

						lifeDie = simpleDateFormat.format(date);
					} catch (ParseException px) {
					}

					if (!datelist.contains(lifeAct)) {
						datelist.add(lifeAct);
					}
					if (!datelist.contains(lifeDie)) {
						datelist.add(lifeDie);
					}
				}
				Collections.sort(datelist);

				// 获取时间段JSONArray
				if (datelist.size() > 0) {
					for (int l = 0; l < datelist.size() - 1; l++) {
						JSONObject joJsonObject = new JSONObject();
						String lifeAct = datelist.get(l);
						String lifeDie = datelist.get(l + 1);

						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 加上时间
						// 必须捕获异常
						try {
							java.util.Date date = simpleDateFormat.parse(lifeDie);
							Calendar calendar = new GregorianCalendar();
							calendar.setTime(date);
							calendar.add(Calendar.DATE, -1);
							date = calendar.getTime();

							lifeDie = simpleDateFormat.format(date);
						} catch (ParseException px) {
						}

						joJsonObject.put("lifeAct", lifeAct);
						joJsonObject.put("lifeDie", lifeDie);
						joJsonObject.put("totalTimeLength", 0);
						joJsonObject.put("advlist", new JSONArray());
						arrayjs.add(joJsonObject);
					}
				}
				// 广告加入时间段JSONArray
				for (int i = 0; i < jArrayLoop.size(); i++) {
					JSONObject jTemJsonObject = jArrayLoop.getJSONObject(i);
					int m_ADid = jTemJsonObject.getIntValue("infosn");
					int timelenght = jTemJsonObject.getIntValue("timelenght");
					String lifeAct = jTemJsonObject.getString("lifeAct");
					String lifeDie = jTemJsonObject.getString("lifeDie");

					if (lifeAct.equals("")) {
						lifeAct = "1999-09-09";
					}
					if (lifeDie.equals("")) {
						lifeDie = "2100-09-09";
					}

					int iact = Integer.parseInt(lifeAct.replace("-", ""));
					int idie = Integer.parseInt(lifeDie.replace("-", ""));

					for (int l = 0; l < arrayjs.size(); l++) {
						JSONObject jsonO = arrayjs.getJSONObject(l);
						int ala = Integer.parseInt(jsonO.getString("lifeAct").replace("-", ""));
						int ald = Integer.parseInt(jsonO.getString("lifeDie").replace("-", ""));
						;

						if (!(idie < ala || iact > ald)) {
							boolean isF = false;
							for (int z = 0; z < arrayjs.getJSONObject(l).getJSONArray("advlist").size(); z++) {
								JSONObject jo = arrayjs.getJSONObject(l).getJSONArray("advlist").getJSONObject(z);
								if (jo.getIntValue("infoid") == m_ADid) {
									arrayjs.getJSONObject(l).getJSONArray("advlist").getJSONObject(z)
											.getJSONArray("offsetlist")
											.add(arrayjs.getJSONObject(l).getIntValue("totalTimeLength"));
									int totalTimeLength = arrayjs.getJSONObject(l).getIntValue("totalTimeLength")
											+ timelenght;
									arrayjs.getJSONObject(l).put("totalTimeLength", totalTimeLength);
									isF = true;
									break;
								}
							}
							if (!isF) {
								JSONObject jObject = new JSONObject();
								jObject.put("infoid", m_ADid);
								jObject.put("timelenght", timelenght);
								JSONArray joffsetlist = new JSONArray();
								joffsetlist.add(arrayjs.getJSONObject(l).getIntValue("totalTimeLength"));
								jObject.put("offsetlist", joffsetlist);
								int totalTimeLength = arrayjs.getJSONObject(l).getIntValue("totalTimeLength")
										+ timelenght;
								arrayjs.getJSONObject(l).put("totalTimeLength", totalTimeLength);

								arrayjs.getJSONObject(l).getJSONArray("advlist").add(jObject);
							}
						}
					}
				}
			}

			return arrayjs;

		} catch (Exception e) {
			return null;
		}
	}

	// 播放列表编码
	public int GetplaylistCodeListbyid(playlist playlist, int pubid, int groupid) {
		try {
			int MaxPackLength = taxigroupMapper.selectByPrimaryKey(groupid).getMaxPackLength();
			GJ_codingCls cls = new GJ_codingCls(MaxPackLength);

			if (playlist != null) {
				GJ_Playlistcls gj_Playlistcls = new GJ_Playlistcls();
				gj_Playlistcls.setM_id(pubid);// 发布id
				gj_Playlistcls.setM_level((byte) (int) playlist.getPlaylistlevel());
				gj_Playlistcls.setM_lifestart(playlist.getPlaylistlifeact());
				gj_Playlistcls.setM_lifeend(playlist.getPlaylistlifedie());

				// 时间段设置
				JSONObject jsonTimequantum = JSONObject.parseObject(playlist.getTimequantum());
				if (jsonTimequantum != null) {
					List<GJ_Timequantumcls> timequantumList = new ArrayList<GJ_Timequantumcls>();
					for (int i = 0; i < jsonTimequantum.getJSONArray("timelist").size(); i++) {
						JSONObject jsonObject = jsonTimequantum.getJSONArray("timelist").getJSONObject(i);
						GJ_Timequantumcls m_Timequantum = new GJ_Timequantumcls();
						m_Timequantum.setM_playstart(jsonObject.getString("lifeAct"));
						m_Timequantum.setM_playend(jsonObject.getString("lifeDie"));
						timequantumList.add(m_Timequantum);
					}
					gj_Playlistcls.setM_Timequantum(timequantumList);
				}
				int Scheduletype = (int) playlist.getScheduletype();
				if (playlist.getPlaylistlevel() == 0)// 如果是转场列表Scheduletype=0
				{
					Scheduletype = 0;
				}
				GJ_Schedulingcls m_Scheduling = new GJ_Schedulingcls();
				List<advertisement> advlist = new ArrayList<advertisement>();
				// 设置排期
				switch (Scheduletype) {
				case 0: {
					m_Scheduling.setM_mode((byte) 0);
					JSONArray jArrayLoop = JSONArray.parseArray(playlist.getProgramlist());
					if (jArrayLoop != null && jArrayLoop.size() > 0) {
						List<GJ_LoopADidlistcls> m_Loop_adidlist = new ArrayList<GJ_LoopADidlistcls>();
						for (int i = 0; i < jArrayLoop.size(); i++) {
							GJ_LoopADidlistcls aDidlistcls = new GJ_LoopADidlistcls();
							int m_ADid = jArrayLoop.getJSONObject(i).getIntValue("infosn");

							advertisement advertisement = advertisementMapper.selectByPrimaryKey(m_ADid);

							// String m_ADidlifestart = advertisement.getlifeAct();
							// String m_ADidlifeend = advertisement.getlifeDie();

							aDidlistcls.setM_ADid(advertisement.getPubid());
							// aDidlistcls.setM_ADidlifestart(m_ADidlifestart);
							// aDidlistcls.setM_ADidlifeend(m_ADidlifeend);
							aDidlistcls.setM_ADidlifestart(jArrayLoop.getJSONObject(i).getString("lifeAct"));
							aDidlistcls.setM_ADidlifeend(jArrayLoop.getJSONObject(i).getString("lifeDie"));
							m_Loop_adidlist.add(aDidlistcls);

							advlist.add(advertisement);
						}
						m_Scheduling.setM_Loop_adidlist(m_Loop_adidlist);
					}
				}
					;
					break;
				case 1: {
					m_Scheduling.setM_mode((byte) 1);
					m_Scheduling.setM_Template_cycle(jsonTimequantum.getIntValue("listcycle"));

					JSONArray jArrayLoop = JSONArray.parseArray(playlist.getProgramlist());
					if (jArrayLoop != null && jArrayLoop.size() > 0) {
						List<GJ_Templatecls> m_Template_adlist = new ArrayList<GJ_Templatecls>();
						for (int i = 0; i < jArrayLoop.size(); i++) {
							JSONObject jTemJsonObject = jArrayLoop.getJSONObject(i);
							GJ_Templatecls gj_Templatecls = new GJ_Templatecls();
							int m_ADid = jTemJsonObject.getIntValue("infoid");
							// 获取广告生命周期
							advertisement advertisement = advertisementMapper.selectByPrimaryKey(m_ADid);
							String m_ADidlifestart = advertisement.getlifeAct();
							String m_ADidlifeend = advertisement.getlifeDie();

							gj_Templatecls.setM_adid(advertisement.getPubid());
							gj_Templatecls.setM_adplaytime(jTemJsonObject.getInteger("timelenght"));
							gj_Templatecls.setM_ADlifestart(m_ADidlifestart);
							gj_Templatecls.setM_ADlifeend(m_ADidlifeend);

							JSONArray jArraytimeoffset = jTemJsonObject.getJSONArray("offsetlist");
							if (jArraytimeoffset != null && jArraytimeoffset.size() > 0) {
								List<String> m_timeoffset = new ArrayList<String>();
								for (int j = 0; j < jArraytimeoffset.size(); j++) {
									m_timeoffset.add(jArraytimeoffset.getString(j));
								}
								gj_Templatecls.setM_timeoffset(m_timeoffset);
							}
							m_Template_adlist.add(gj_Templatecls);

							advlist.add(advertisement);
						}
						m_Scheduling.setM_Template_adlist(m_Template_adlist);
					}
				}
					;
					break;
				case 2: {
					m_Scheduling.setM_mode((byte) 1);

					m_Scheduling.setM_Template_cycle(jsonTimequantum.getIntValue("listcycle"));
				}
					;
					break;
				}

				gj_Playlistcls.setM_Scheduling(m_Scheduling);

				List<byte[]> SendByteList = cls.GJ_AddPlayList(GJ_xieyiTypeEnum.GJ.getValue(),
						GJ_xieyiVersionEnum.one.getValue(), gj_Playlistcls);
				int returnCrc = gj_Playlistcls.getM_crc();

				if (SendByteList != null && SendByteList.size() > 0) {
					// playlistcodeMapper.deleteByPrimaryKey(playlist.getId());
					JSONArray jArray = new JSONArray();
					int PackLength = 0;
					for (int i = 0; i < SendByteList.size(); i++) {
						byte[] bytes = SendByteList.get(i);
						PackLength += bytes.length;
						StringBuilder buf = new StringBuilder(bytes.length * 2);
						for (byte b : bytes) { // 使用String的format方法进行转换
							buf.append(String.format("%02x", new Integer(b & 0xff)));
						}
						jArray.add(buf.toString().trim());
					}
					String containADID = "";
					for (int i = 0; i < advlist.size(); i++) {
						containADID += advlist.get(i).getPubid() + ",";
					}
					if (containADID.length() > 0) {
						containADID = containADID.substring(0, containADID.length() - 1);
					}
					int rowcout = playlistcodeMapper.selectcoutByplaylistSN(playlist.getplaylistSN());

					playlistcodeMapper.deletecodeByDelindex();

					if (rowcout <= 0) {
						playlistcode record = new playlistcode();
						record.setGroupid(groupid);
						record.setContainADID(containADID);
						record.setLifeAct("1999-09-09");
						record.setLifeDie("2100-09-09");
						record.setPubid(pubid);
						record.setPlaylistsn(playlist.getplaylistSN());
						record.setCodecontext(jArray.toJSONString());
						record.setPlaylistcrc(returnCrc);
						record.setPackCount(SendByteList.size());
						record.setPackLength(PackLength);

						playlistcodeMapper.insert(record);
					} else {
						playlistcode record = new playlistcode();
						record.setGroupid(groupid);
						record.setContainADID(containADID);
						record.setLifeAct("1999-09-09");
						record.setLifeDie("2100-09-09");
						record.setPubid(pubid);
						record.setPlaylistsn(playlist.getplaylistSN());
						;
						record.setCodecontext(jArray.toJSONString());
						record.setPlaylistcrc(returnCrc);
						record.setPackCount(SendByteList.size());
						record.setPackLength(PackLength);

						playlistcodeMapper.updateByplaylistSN(record);
					}

					NettyClient.TcpSocketSendPublish(advlist, null);
				}
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 1;
		}
	}

	// 播放列表编码
	public int GetplaylistCodeListbyid(int playlistSN, JSONObject playlist, int pubid, int level, String Timequantum,
			int groupid) {
		try {
			int MaxPackLength = taxigroupMapper.selectByPrimaryKey(groupid).getMaxPackLength();
			GJ_codingCls cls = new GJ_codingCls(MaxPackLength);

			if (playlist != null) {
				GJ_Playlistcls gj_Playlistcls = new GJ_Playlistcls();
				gj_Playlistcls.setM_id(pubid);// 发布id
				gj_Playlistcls.setM_level((byte) (int) level);
				String lifeAct = playlist.getString("lifeAct");
				String lifeDie = playlist.getString("lifeDie");
				if (!lifeAct.equals("1999-09-09")) {
					gj_Playlistcls.setM_lifestart(playlist.getString("lifeAct"));
				}
				if (!lifeDie.equals("2100-09-09")) {
					gj_Playlistcls.setM_lifeend(playlist.getString("lifeDie"));
				}

				// 时间段设置
				JSONObject jsonTimequantum = JSONObject.parseObject(Timequantum);
				if (jsonTimequantum != null) {
					List<GJ_Timequantumcls> timequantumList = new ArrayList<GJ_Timequantumcls>();
					for (int i = 0; i < jsonTimequantum.getJSONArray("timelist").size(); i++) {
						JSONObject jsonObject = jsonTimequantum.getJSONArray("timelist").getJSONObject(i);
						GJ_Timequantumcls m_Timequantum = new GJ_Timequantumcls();
						m_Timequantum.setM_playstart(jsonObject.getString("lifeAct"));
						m_Timequantum.setM_playend(jsonObject.getString("lifeDie"));
						timequantumList.add(m_Timequantum);
					}
					gj_Playlistcls.setM_Timequantum(timequantumList);
				}

				GJ_Schedulingcls m_Scheduling = new GJ_Schedulingcls();
				// List<advertisement> advlist=new ArrayList<advertisement>();
				// 设置排期

				m_Scheduling.setM_mode((byte) 1);
				gj_Playlistcls.setM_Scheduling(m_Scheduling);

				m_Scheduling.setM_Template_cycle(playlist.getIntValue("totalTimeLength"));
				String containADID = "";// 包含的广告发布id
				JSONArray jArrayLoop = playlist.getJSONArray("advlist");
				if (jArrayLoop != null && jArrayLoop.size() > 0) {
					List<GJ_Templatecls> m_Template_adlist = new ArrayList<GJ_Templatecls>();
					for (int i = 0; i < jArrayLoop.size(); i++) {
						JSONObject jTemJsonObject = jArrayLoop.getJSONObject(i);
						int m_ADid = jTemJsonObject.getIntValue("infoid");
						advertisement advertisement = advertisementMapper.selectByPrimaryKey(m_ADid);
						int pid = advertisement.getPubid();
						containADID += pid + ",";
						GJ_Templatecls gj_Templatecls = new GJ_Templatecls();
						// 获取广告生命周期
						String m_ADidlifestart = advertisement.getlifeAct();
						String m_ADidlifeend = advertisement.getlifeDie();

						gj_Templatecls.setM_adid(advertisement.getPubid());
						gj_Templatecls.setM_adplaytime(jTemJsonObject.getInteger("timelenght"));
						gj_Templatecls.setM_ADlifestart(m_ADidlifestart);
						gj_Templatecls.setM_ADlifeend(m_ADidlifeend);

						JSONArray jArraytimeoffset = jTemJsonObject.getJSONArray("offsetlist");
						if (jArraytimeoffset != null && jArraytimeoffset.size() > 0) {
							List<String> m_timeoffset = new ArrayList<String>();
							for (int j = 0; j < jArraytimeoffset.size(); j++) {
								m_timeoffset.add(jArraytimeoffset.getString(j));
							}
							gj_Templatecls.setM_timeoffset(m_timeoffset);
						}
						m_Template_adlist.add(gj_Templatecls);

						// advlist.add(advertisement);
					}
					m_Scheduling.setM_Template_adlist(m_Template_adlist);
				}

				List<byte[]> SendByteList = cls.GJ_AddPlayList(GJ_xieyiTypeEnum.GJ.getValue(),
						GJ_xieyiVersionEnum.one.getValue(), gj_Playlistcls);
				int returnCrc = gj_Playlistcls.getM_crc();

				if (SendByteList != null && SendByteList.size() > 0) {
					// playlistcodeMapper.deleteByPrimaryKey(playlist.getId());
					JSONArray jArray = new JSONArray();
					int PackLength = 0;
					for (int i = 0; i < SendByteList.size(); i++) {
						byte[] bytes = SendByteList.get(i);
						PackLength += bytes.length;
						StringBuilder buf = new StringBuilder(bytes.length * 2);
						for (byte b : bytes) { // 使用String的format方法进行转换
							buf.append(String.format("%02x", new Integer(b & 0xff)));
						}
						jArray.add(buf.toString().trim());
					}
					if (containADID.length() > 0) {
						containADID = containADID.substring(0, containADID.length() - 1);
					}

					playlistcodeMapper.deletecodeByDelindex();

					playlistcode record = new playlistcode();
					record.setGroupid(groupid);
					record.setContainADID(containADID);
					record.setLifeAct(playlist.getString("lifeAct"));
					record.setLifeDie(playlist.getString("lifeDie"));
					record.setPubid(pubid);
					record.setPlaylistsn(playlistSN);
					record.setCodecontext(jArray.toJSONString());
					record.setPlaylistcrc(returnCrc);
					record.setPackCount(SendByteList.size());
					record.setPackLength(PackLength);

					playlistcodeMapper.insert(record);
					/*
					 * int rowcout = playlistcodeMapper.selectcoutByplaylistSN(playlistSN);
					 * if(rowcout<=0) { playlistcode record=new playlistcode();
					 * record.setPubid(pubid); record.setPlaylistsn(playlistSN);
					 * record.setCodecontext(jArray.toJSONString());
					 * record.setPlaylistcrc(returnCrc); record.setPackCount(SendByteList.size());
					 * record.setPackLength(PackLength);
					 * 
					 * playlistcodeMapper.insert(record); } else { playlistcode record=new
					 * playlistcode(); record.setPubid(pubid); record.setPlaylistsn(playlistSN);;
					 * record.setCodecontext(jArray.toJSONString());
					 * record.setPlaylistcrc(returnCrc); record.setPackCount(SendByteList.size());
					 * record.setPackLength(PackLength);
					 * 
					 * playlistcodeMapper.updateByplaylistSN(record); }
					 */
					// NettyClient.TcpSocketSendPublish(advlist, null);
				}
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 1;
		}
	}
}
