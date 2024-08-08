package gudiSpring.user.dto;

import java.util.Date;

public class UserDto {
	private int userNo;
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private Date createdDate;
    private Date updateDate;
    private String authority;
    private String userLeave;
   

    public UserDto() {}
    
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getUserLeave() {
		return userLeave;
	}
	public void setUserLeave(String userLeave) {
		this.userLeave = userLeave;
	}
	@Override
	public String toString() {
		return "UserDto [userNo=" + userNo + ", id=" + id + ", password=" + password + ", name=" + name + ", nickname="
				+ nickname + ", phone=" + phone + ", email=" + email + ", createdDate=" + createdDate + ", updateDate="
				+ updateDate + ", authority=" + authority + ", userLeave=" + userLeave + "]";
	}
	public UserDto(int userNo, String id, String password, String name, String nickname, String phone, String email,
			Date createdDate, Date updateDate, String authority, String userLeave) {
		super();
		this.userNo = userNo;
		this.id = id;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.phone = phone;
		this.email = email;
		this.createdDate = createdDate;
		this.updateDate = updateDate;
		this.authority = authority;
		this.userLeave = userLeave;
	}
	
	
	
	
	
}
