package com.projectwork.quintoportale.view;

public class UpdatePasswordDTO {
	private String oldPassword;
	private String newPassword;
	
	public UpdatePasswordDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdatePasswordDTO(String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
