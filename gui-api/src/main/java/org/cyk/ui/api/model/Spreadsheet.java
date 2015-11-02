package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.utility.common.cdi.BeanAdapter;

@Getter @Setter @NoArgsConstructor
public class Spreadsheet<SPREADSHEET,ROW,COLUMN,CELL> implements Serializable {

	private static final long serialVersionUID = -6574548621164268562L;

	protected SPREADSHEET spreadsheet;
	protected List<ROW> rows;
	protected List<COLUMN> columns;
	protected List<CELL> cells;
	protected Boolean editable = Boolean.FALSE;
	protected String rowHeader="HEADER",title="TITLE";
	protected Collection<SpreadsheetListener> listeners = new ArrayList<>();

	public Spreadsheet(SPREADSHEET spreadsheet,List<ROW> rows,List<COLUMN> columns, List<CELL> cells) {
		super();
		this.spreadsheet = spreadsheet;
		this.rows = rows;
		this.columns = columns;
		this.cells = cells;
	}
	
	public CELL cell(Integer row, Integer column) {
		return cells.get(row * columns.size() + column);
	}
	
	public String cellText(Integer row, Integer column) {
		int index = row * columns.size() + column;
		Object value = null;
		for(SpreadsheetListener listener : listeners){
			Object v = listener.getCellValue(index);
			if(v!=null)
				value = v;
		}
		return value==null?"":value.toString();
	}
	
	public String columnText(Integer index) {
		Object value = null;
		for(SpreadsheetListener listener : listeners){
			Object v = listener.getColumnTitle(index);
			if(v!=null)
				value = v;
		}
		return value==null?"":value.toString();
	}
	
	public String rowText(Integer index) {
		Object value = null;
		for(SpreadsheetListener listener : listeners){
			Object v = listener.getRowTitle(index);
			if(v!=null)
				value = v;
		}
		return value==null?"":value.toString();
	}
	
	/**/
	
	public static interface SpreadsheetListener{
		Object getColumnTitle(Integer index);
		Object getRowTitle(Integer index);
		Object getCellValue(Integer index);
	}
	
	public static class SpreadsheetAdapter extends BeanAdapter implements SpreadsheetListener{
		private static final long serialVersionUID = 4768546989989564581L;

		@Override
		public Object getColumnTitle(Integer index) {
			return null;
		}

		@Override
		public Object getRowTitle(Integer index) {
			return null;
		}

		@Override
		public Object getCellValue(Integer index) {
			return null;
		}
		
	}

}