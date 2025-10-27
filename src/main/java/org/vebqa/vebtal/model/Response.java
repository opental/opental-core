package org.vebqa.vebtal.model;

public class Response {

	public static final String PASSED = "0";
	
	public static final String FAILED = "1";
	
	/**
	 * data model
	 */

	/**
	 * a message, e.g sucess oder detailed failure
	 */
	private String message;
	
	/**
	 * a code, 0 for success, 1 for error
	 */
	private String code;
	
	/**
	 * in case of saving a value with a store* command, the key will be saved here
	 */
	private String storedKey;
	
	/**
	 * in case of saving a value with a store* command, the value will be save here
	 */
	private String storedValue;
	
	
	Response() { }
	
	Response(Builder builder) {
		this.message = builder.message;
	    this.code = builder.code;
	    this.storedKey = builder.storedKey;
	    this.storedValue = builder.storedValue;
	}
	
	public String getStoredKey() {
		return storedKey;
	}
	
	public void setStoredKey(String aStoredKey) {
		this.storedKey = aStoredKey;
	}

	public String getStoredValue() {
		return storedValue;
	}
	
	public void setStoredValue(String aStoredValue) {
		this.storedValue = aStoredValue;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String aMessage) {
		this.message = aMessage;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String aCode) {
		this.code = aCode;
	}
	
	public String toString() {
		if (this.storedKey == null || this.getStoredKey().isEmpty()) {
			return "[Code: " + this.code + ", " + "Message: " + this.message + "]";
		}
		return "[Code: " + this.code + ", " + "Message: " + this.message + ", " + "StoredKey: " + this.storedKey + ", " + "StoredValue: " + this.storedValue + "]";
	}
	
    public static class Builder {
		private String message;
	    private String code;
	    private String storedKey;
	    private String storedValue;

        public Builder setMessage(String aMessage) {
            this.message = aMessage;
            return this;
        }

        public Builder setCode(String aCode) {
            this.code = aCode;
            return this;
        }

        public Builder setStoredKey(String storedKey) {
            this.storedKey = storedKey;
            return this;
        }

        public Builder setStoredValue(String storedValue) {
            this.storedValue = storedValue;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}