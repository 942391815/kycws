package com.wxy.dg.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.wxy.dg.common.util.CommonUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelUtil {
	
	public static List<List<String>> readExcel(InputStream is) {
		List<List<String>> contents = new ArrayList<List<String>>();
		Workbook wb = null;
		try {
			// 构造Workbook（工作薄）对象
			wb = Workbook.getWorkbook(is);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wb == null)
			return null;

		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheet = wb.getSheets();
		if (sheet != null && sheet.length > 0) {
			// 得到当前工作表的行数
			int rowNum = sheet[0].getRows();
			for (int j = 1; j < rowNum; j++) {
				// 得到当前行的所有单元格
				List<String> rowContent = new ArrayList<String>();
				Cell[] cells = sheet[0].getRow(j);
				if (isNullRow(cells)) {
					break;
				}
				if (cells != null && cells.length > 0) {
					// 对每个单元格进行循环
					for (int k = 0; k < cells.length; k++) {
						// 读取当前单元格的值
						rowContent.add(cells[k].getContents());
					}
				}
				contents.add(rowContent);
			}
		}
		// 最后关闭资源，释放内存
		wb.close();
		return contents;
	}

	// 判断是否为空行
	public static boolean isNullRow(Cell[] cells) {

		for (Cell cell : cells) {
			if (CommonUtil.isNotNull(cell.getContents())) {
				return false;
			}
		}
		return true;
	}
}
