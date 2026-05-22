package com.davalores.crypto.provider.domain.model.ripio.caas.api;

public class EndUserDto {
	private String external_ref;
	private String email;
	private String created_at;
	private boolean has_billing_info;
	private String billing_info_updated_at;
	public String getExternal_ref() {
		return external_ref;
	}
	public void setExternal_ref(String external_ref) {
		this.external_ref = external_ref;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public boolean isHas_billing_info() {
		return has_billing_info;
	}
	public void setHas_billing_info(boolean has_billing_info) {
		this.has_billing_info = has_billing_info;
	}
	public String getBilling_info_updated_at() {
		return billing_info_updated_at;
	}
	public void setBilling_info_updated_at(String billing_info_updated_at) {
		this.billing_info_updated_at = billing_info_updated_at;
	}
	
	@Override
	public String toString() {
		return "EndUserDto [external_ref=" + external_ref + ", email=" + email + ", created_at=" + created_at
				+ ", has_billing_info=" + has_billing_info + ", billing_info_updated_at=" + billing_info_updated_at
				+ "]";
	}
	
	
}
