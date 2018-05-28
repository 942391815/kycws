package com.wxy.dg.common.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.wxy.dg.common.config.Global;
import com.wxy.dg.common.util.CommonUtil;

public class WriteExcelUtil {

	/**
	 * 把內容写入excel文件中
	 * 
	 */
	public static void writeExcel(OutputStream os, List<String> headers,
			List<List<String>> infos) {
		WritableWorkbook wwb = null;
		try {
			// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
			wwb = Workbook.createWorkbook(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			WritableSheet ws = wwb.createSheet("sheet1", 0);
			if (null == headers || headers.size() < 1) {
				return;
			}

			// 设置列标题样式
			WritableCellFormat cFormat = new WritableCellFormat();
			try {
				cFormat.setBackground(Colour.LIGHT_GREEN);
			} catch (WriteException e1) {
				e1.printStackTrace();
			}
			// 保存最佳列宽数据的数组
			int columnBestWidth[] = new int[headers.size()];
			// 下面开始添加单元格
			for (int j = 0; j < headers.size(); j++) {

				Label labelH = new Label(j, 0, headers.get(j), cFormat);

				try {
					// 将生成的单元格添加到工作表中
					ws.addCell(labelH);
					// 汉字占2个单位长度
					int headerWidth = headers.get(j).length()
							+ getChineseNum(headers.get(j));
					// 求取到目前为止的最佳列宽
					if (columnBestWidth[j] < headerWidth) {
						columnBestWidth[j] = headerWidth;
					}
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}

			if (null == infos) {
				return;
			}

			int distanceTarget = Integer.valueOf(Global.getDistanceTarget());
			for (int i = 0; i < infos.size(); i++) {
				List<String> rowInfo = infos.get(i);
				if (rowInfo != null && rowInfo.size() == headers.size()) {
					for (int j = 0; j < rowInfo.size(); j++) {
						// 考勤不正常的单元格背景色
						WritableCellFormat cellFormat = new WritableCellFormat();
						Label labelC = null;
						if(j > 2 && j < rowInfo.size()-1 && Double.valueOf(rowInfo.get(j)) < distanceTarget) {
							try {
								cellFormat.setBackground(Colour.RED);
								labelC = new Label(j, i + 1, rowInfo.get(j),cellFormat);
							} catch (WriteException e) {
								e.printStackTrace();
							}
						} else {
							labelC = new Label(j, i + 1, rowInfo.get(j));
						}
						try {
							// 将生成的单元格添加到工作表中
							ws.addCell(labelC);
							// 汉字占2个单位长度
							int rowWidth = 0;
							if(CommonUtil.isNotNull(rowInfo.get(j))) {
								rowWidth = rowInfo.get(j).length() + getChineseNum(rowInfo.get(j));
							}
							// 求取到目前为止的最佳列宽
							if (columnBestWidth[j] < rowWidth) {
								columnBestWidth[j] = rowWidth;
							}
						} catch (RowsExceededException e) {
							e.printStackTrace();
						} catch (WriteException e) {
							e.printStackTrace();
						}

					}
				}
			}

			for (int i = 0; i < columnBestWidth.length; i++) { //设置每列宽
				ws.setColumnView(i, columnBestWidth[i] + 2);
			}
			try {
				// 从内存中写入文件中
				wwb.write();
				// 关闭资源，释放内存
				wwb.close();
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getChineseNum(String context) { // /统计context中是汉字的个数
		int lenOfChinese = 0;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]"); // 汉字的Unicode编码范围
		Matcher m = p.matcher(context);
		while (m.find()) {
			lenOfChinese++;
		}
		return lenOfChinese;
	}
}