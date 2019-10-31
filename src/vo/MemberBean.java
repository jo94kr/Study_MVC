package vo;

public class MemberBean {
	/*
	 * member 테이블
	 * ------------------------------------
	 * 번호(idx) - INT, PK, 자동증가(AI)
	 * 이름(name) - VARCHAR(12), NN
	 * 성별(gender) - VARCHAR(1), NN
	 * 나이(age) - INT, NN
	 * E-Mail(email) - VARCHAR(50), NN, UN
	 * 아이디(id) - VARCHAR(16) NN UN
	 * 패스워드(passwd) - VARCHAR(20) NN
	 * ------------------------------------
	 	CREATE TABLE member (
			idx INT PRIMARY KEY AUTO_INCREMENT,
			name VARCHAR(12) NOT NULL,
			gender VARCHAR(1) NOT NULL,
			age INT NOT NULL,
			email VARCHAR(50) NOT NULL UNIQUE,
			id VARCHAR(16) NOT NULL UNIQUE,
			passwd VARCHAR(20) NOT NULL
		);
	 */
	private int idx;
	private String name;
	private String gender;
	private int age;
	private String email;
	private String id;
	private String passwd;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
}

























