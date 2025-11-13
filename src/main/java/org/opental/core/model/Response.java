package org.opental.core.model;


/**
 * data model for a testing response
 */
public class Response {

	/**
	 * Representation for passed step
	 */
	public static final String PASSED = "0";
	
	/**
	 * Representation for failed step
	 */
	public static final String FAILED = "1";

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
	
	
	/**
	 * standard constructor
	 */
	Response() { }
	
	/**
	 * constructor for a builder
	 * @param builder Builder Object
	 */
	Response(Builder builder) {
		this.message = builder.message;
	    this.code = builder.code;
	    this.storedKey = builder.storedKey;
	    this.storedValue = builder.storedValue;
	}
	
	/**
	 * get the key for the returned value
	 * @return store key
	 */
	public String getStoredKey() {
		return storedKey;
	}
	
	/**
	 * set the key for the returning store
	 * @param aStoredKey storedKey
	 */
	public void setStoredKey(String aStoredKey) {
		this.storedKey = aStoredKey;
	}

	/**
	 * get the value for the returning store
	 * @return the value
	 */
	public String getStoredValue() {
		return storedValue;
	}
	
	/**
	 * set the value for the returning store
	 * @param aStoredValue storedValue
	 */
	public void setStoredValue(String aStoredValue) {
		this.storedValue = aStoredValue;
	}

	/**
	 * get the message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * set the message
	 * @param aMessage message
	 */
	public void setMessage(String aMessage) {
		this.message = aMessage;
	}

	/**
	 * get the code, representing passed or failed
	 * @return code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * set the code, representing passed or failed
	 * @param aCode code
	 */
	public void setCode(String aCode) {
		this.code = aCode;
	}
	
	/**
	 * convienience, better output
	 * @return some text representing the response
	 */
	@Override
	public String toString() {
		if (this.storedKey == null || this.getStoredKey().isEmpty()) {
			return "[Code: " + this.code + ", " + "Message: " + this.message + "]";
		}
		return "[Code: " + this.code + ", " + "Message: " + this.message + ", " + "StoredKey: " + this.storedKey + ", " + "StoredValue: " + this.storedValue + "]";
	}
	
	/**
	 * Builder class for our Response
	 */
    public static class Builder {
    	/**
    	 * message
    	 */
		private String message;
		/**
		 * code
		 */
	    private String code;
	    /**
	     * storesKey
	     */
	    private String storedKey;
	    /**
	     * storedValue
	     */
	    private String storedValue;

	    /**
	     * set the message
	     * @param aMessage message
	     * @return Builder
	     */
        public Builder setMessage(String aMessage) {
            this.message = aMessage;
            return this;
        }

        /**
         * set the code
         * @param aCode code
         * @return Builder
         */
        public Builder setCode(String aCode) {
            this.code = aCode;
            return this;
        }

        /**
         * set the store key
         * @param storedKey storedKey
         * @return Builder
         */
        public Builder setStoredKey(String storedKey) {
            this.storedKey = storedKey;
            return this;
        }

        /**
         * set the store value
         * @param storedValue storedValue
         * @return Builder
         */
        public Builder setStoredValue(String storedValue) {
            this.storedValue = storedValue;
            return this;
        }

        /**
         * finished - build the object
         * @return Response
         */
        public Response build() {
            return new Response(this);
        }
    }
}