package rawdata;

public class Row {
	private String data;
	private String first;
	private String last;
	
	public Row(String data) {
		this.data = data;
		String tmp[] = data.split(",");
		first = tmp[0];
		last = tmp[tmp.length-1];
	}
	
	public String getData(boolean withFirst, boolean withLast) {
		String result = new String();
		String tmp[] = data.split(",");
		int start = 0;
		int end = tmp.length;
		if (!withFirst)	start++;
		if (!withLast)  end--;
		for (;start < end; ++start){
			result += tmp[start] + " ";
		}
		return result;
	}
	
	public String getFirst() {
		return first;
	}
	public String getLast() {
		if (last.equals("2"))
			return "0.0";
		else
			return "1.0";
	}
	public String getData() {
		String result = new String();
		String tmp[] = data.split(",");
		int start = 1;
		int end = tmp.length-1;

		for (;start < end; ++start){
			if (!tmp[start].equals("?"))
				result += tmp[start] + ".0 ";
			else
				result += "1.0 ";
		}
		return result;
	}

}
