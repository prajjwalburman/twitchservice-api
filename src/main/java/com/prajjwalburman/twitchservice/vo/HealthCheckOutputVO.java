package com.prajjwalburman.twitchservice.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class HealthCheckOutputVO {
	
	String returnCode;
	String returnMessage;
	
	public String getReturnCode() {
		return returnCode;
	}
	
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	public String getReturnMessage() {
		return returnMessage;
	}
	
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((returnCode == null) ? 0 : returnCode.hashCode());
		result = prime * result + ((returnMessage == null) ? 0 : returnMessage.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HealthCheckOutputVO other = (HealthCheckOutputVO) obj;
		if (returnCode == null) {
			if (other.returnCode != null)
				return false;
		} else if (!returnCode.equals(other.returnCode))
			return false;
		if (returnMessage == null) {
			if (other.returnMessage != null)
				return false;
		} else if (!returnMessage.equals(other.returnMessage))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "HealthCheckOutputVO [returnCode=" + returnCode + ", returnMessage=" + returnMessage + "]";
	}
	
	

}
