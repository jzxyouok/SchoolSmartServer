package bean;

/**
 * 鍟嗗搧绫�
 */
public class Good {
    private int id;//鍚宬ey
    private String name;
    private double price;
    private String img;
    private int num;
    private int type;//1浠ｈ〃瓒呭競锛�浠ｈ〃澶栧崠锛�浠ｈ〃鏃╅锛�浠ｈ〃鍥㈣喘
    private String time;
    private int favorable_comment;
    private int bad_comment;
    

    
    public int getFavorable_comment() {
		return favorable_comment;
	}

	public void setFavorable_comment(int favorable_comment) {
		this.favorable_comment = favorable_comment;
	}

	public int getBad_comment() {
		return bad_comment;
	}

	public void setBad_comment(int bad_comment) {
		this.bad_comment = bad_comment;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
