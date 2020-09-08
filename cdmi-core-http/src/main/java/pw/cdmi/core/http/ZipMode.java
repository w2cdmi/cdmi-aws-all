package pw.cdmi.core.http;


public enum ZipMode {
	
    Gzip("gzip"),
    Encrypt("encrypt");
    /************************************/
    /** 每个Slice文件的最头一排 为引用值 **/
    /************************************/
    public static ZipMode fromValue(String zipMode) throws IllegalArgumentException {
        for (ZipMode zip : ZipMode.values()) {
            if (zip.toString().equalsIgnoreCase(zipMode)) return zip;
        }

        throw new IllegalArgumentException(
                "Cannot create enum from " + zipMode + " value!");
    }


    private final String mode;

    private ZipMode(String mode) {
        this.mode = mode;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return mode;
    }
}
