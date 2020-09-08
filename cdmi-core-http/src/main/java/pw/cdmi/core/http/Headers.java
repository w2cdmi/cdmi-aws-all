package pw.cdmi.core.http;

public interface Headers {

    /*
     * Standard HTTP Headers
     */
	
	public static final String AUTHORIZATION = "Authorization";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";//Delete,Google Not
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_MD5 = "Content-MD5";
    public static final String CONTENT_RANGE = "Content-Range";//Add, Google
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DATE = "Date";
    public static final String ETAG = "ETag";	//New, Google
    public static final String HOST = "Host";	//New, Google
    public static final String ACCEPT = "Accept"; //New, Google Not
    public static final String IF_MATCH = "If-Match";
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String IF_NONE_MATCH = "If-None-Match";
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    public static final String LAST_MODIFIED = "Last-Modified";
    public static final String LOCATION = "Location"; //New Add
    public static final String RANGE = "Range"; 
    public static final String SERVER = "Server";
    
    
    
    /*
     * Extension (Custom) HTTP Headers
     */

    /** S3 response header for a request's AWS request ID */
    public static final String REQUEST_ID = "x-cdmi-request-id";
    public static final String HOST_ID = "x-cdmi-host-id";
    /** AWS response header for a request's AWS Resource Path */
    public static final String RESOURCE_PATH = "x-cdmi-resource";
    
    /** 用以区分存储容器的类别：x-cdmi-class=file|basic */
//    public static final String CDMI_CLASS = "x-cdmi-class";
    
    /** 数据在该站点内可通过src=/MyContainer/MyObject?link方式访问:x-cdmi-site */
//    public static final String CDMI_SITE = "x-cdmi-site";
    
    /** 数据可通过服务前缀方式查询，相当于对存储的数据提供了一种分类机制：x-cdmi-prefix */
//    public static final String CDMI_PREFIX = "x-cdmi-prefix";

    /** 允许容器的最大版本数，不应该操作系统最大允许的版本数量 */
    public static final String CDMI_MAX_VERSION_NUM= "x-cdmi-version-num";
    
    /** 设置存储数据时候允许被外部网站以外链形式访问：x-cdmi-link-limit=private|protected|public */
    public static final String CDMI_LIMIT= "x-cdmi-limit";
    
    /** 用户自定义的数据元数据前缀：x-cdmi-meta- */
    public static final String CDMI_USER_METADATA_PREFIX = "x-cdmi-meta-";
    
    /** 是否同时获取元数据: x-cdmi-request-metadata=true|false */
    public static final String CDMI_REQUEST_METADATA = "x-cdmi-request-metadata";

    /** 上传对象的MD5值，客户端计算后传入，该值以CONTENT_MD5为准：x-cdmi-content-md5 */
    public static final String CDMI_CONTENT_MD5 = "x-cdmi-content-md5";

    /** 该标签决定了上传对象是否在服务端进行压缩:x-cdmi-zip-mode=gzip 表示以gzip方式进行压缩 */
    public static final String CDMI_ZIP_MODE = "x-cdmi-zip-mode";
    
    /** 该标签决定了上传对象是否在服务端进行加密:x-cdmi-encrypt=true|false */
    public static final String CDMI_CONTEXT_ENCRYPT = "x-cdmi-encrypt";
    
    /** 分片上传对象时，数据片的起始位置：x-cdmi-slice-offset */
    public static final String CDMI_SLICE_OFFSET = "x-cdmi-slice-offset";
    
    /** 分片上传对象时，标识是否是最后一片: x-cdmi-slice-last=true|false */
    public static final String CDMI_SLICE_LAST = "x-cdmi-slice-last";

    /** 分片获取对象时，每次获取分片的长度: x-cdmi-slice-length */
    public static final String CDMI_SLICE_LENGTH = "x-cdmi-slice-length";
    
    /** 复制一个文件成新的文件时，复制源在云中的位置：x-cdmi-copy-source */
    public static final String CDMI_COPY_SOURCE= "x-cdmi-copy-source";

    /** 复制文件时，元数据是否复制或是用新的替换：x-cdmi-metadata-replace=true|false */
    public static final String CDMI_METADATA_REPLACE = "x-cdmi-metadata-replace";
    
    
    /********************扩展数据服务：文件目录服务******************************/    
    
    /** 移动或重命名文件：x-cdmi-move-source */
    public static final String CDMI_MOVE_SOURCE= "x-cdmi-move-source";
    
    /** 从指定的DIR中获取文件：x-cdmi-file-dir */
    public static final String CDMI_FILE_DIR = "x-cdmi-file-dir";
    
    /** 显示指定的DIR中的文件或文件夹：x-cdmi-file-view=ALL/FOLDER/FILE */
    public static final String CDMI_FILE_VIEW = "x-cdmi-file-view";
    
    
    /********************扩展数据服务：文件公共访问服务***************************/
    
    /** 公共访问文件的分类前缀：x-cdmi-public-prefix */
    public static final String CDMI_PUBLIC_PREFIX = "x-cdmi-public-prefix";
    
    /** 访问公共文件的提取码：x-cdmi-public-code */
    public static final String CDMI_PUBLIC_CODE = "x-cdmi-public-code";
   
    /** 公共访问文件的访问的限制次数：x-cdmi-public-limit-number */
    public static final String CDMI_PUBLIC_LIMIT_NUMBER = "x-cdmi-public-limit-number";
    
    /** 公共访问文件的失效访问时间：x-cdmi-public-invalidation-Date */
    public static final String CDMI_PUBLIC_INVALIDATION_DATE = "x-cdmi-public-invalidation-Date";
    

    /********************扩展数据服务：文件外链限制******************************/
    
    /** 用于获取指定目录下的外链对象：x-cdmi-link-dir */
    public static final String CDMI_LINK_DIR = "x-cdmi-link-dir";
    
    /** 该路径下的文件用于外链：x-cdmi-link-path */
    public static final String CDMI_LINK_PATH = "x-cdmi-link-path";

    /** 可外链访问资源的网站地址：x-cdmi-linksite- */
    public static final String CDMI_LINK_SITE = "x-cdmi-linksite-";
    

    /********************扩展数据服务：生成缩略图********************************/
    
    /** 缩略图的来源文件：x-cdmi-thumbnail-source */
    public static final String CDMI_THUMBNAIL_SOURCE= "x-cdmi-thumbnail-source";
    
    /** 缩略图图片的高度：x-cdmi-thumbnail-height */
    public static final String CDMI_THUMBNAIL_HEIGHT= "x-cdmi-thumbnail-height";
    
    /** 缩略图图片的宽度：x-cdmi-thumbnail-width */
    public static final String CDMI_THUMBNAIL_WIDTH= "x-cdmi-thumbnail-width";
    
    /** 缩略图图片的缩比方式：x-cdmi-thumbnail-zoom=scale|cut|tensile */
    public static final String CDMI_THUMBNAIL_ZOOM= "x-cdmi-thumbnail-zoom";
    
    
    /********************扩展数据服务：格式转化***********************************/
    
    /** 需要格式转化的源文件：x-cdmi-converter-source */
    public static final String CDMI_CONVERTER_SOURCE= "x-cdmi-converter-source";
    
    /** 格式转化后源文件是否删除：x-cdmi-converter-delete=true|false */
    public static final String CDMI_CONVERTER_DELETE= "x-cdmi-converter-delete";
    
    
    /********************扩展数据服务：离线下载***********************************/
    
    /** 离线下载的源文件：x-cdmi-download-source */
    public static final String CDMI_DOWNLOAD_SOURCE= "x-cdmi-download-source";
    
    
    /********************扩展数据服务：全文检索***********************************/
    
    /** 全文检索的文件范围：x-cdmi-search-path */
    public static final String CDMI_SEARCH_PATH= "x-cdmi-search-path";
    
    /** 全文检索的检索关键字：x-cdmi-search-keyword */
    public static final String CDMI_SEARCH_KEYWORD= "x-cdmi-search-keyword";

    /** 全文检索的文件类型范围：x-cdmi-search-filetype */
    public static final String CDMI_SEARCH_FILETYPE= "x-cdmi-search-filetype";

    /** 检索结果所附带的摘要关键字颜色：x-cdmi-search-summary-render */
    public static final String CDMI_SEARCH_SUMMARY_COLOR= "x-cdmi-search-summary-render";
    
    /** 根据文件的创建时间进行检索：x-cdmi-search-begin-date */
    public static final String CDMI_SEARCH_BEGIN_DATE= "x-cdmi-search-begin-date";
    
    /** 根据文件的创建时间范围进行检索：x-cdmi-search-end-date */
    public static final String CDMI_SEARCH_END_DATE= "x-cdmi-search-end-date";

    /********************系统扩展：异步任务**************************************/    
    
    /** 异步执行任务的前缀：x-cdmi-task-prefix */
    public static final String CDMI_TASK_PREFIX= "x-cdmi-task-prefix";
    
    /** 异步任务的名称：x-cdmi-task-name=formatConverter|download|snapshot */
    public static final String CDMI_TASK_NAME= "x-cdmi-task-name";
    
    /** 系统随机产生的安全令牌：x-cdmi-security-token */
    public static final String CDMI_SECURITY_TOKEN = "x-cdmi-security-token";
    
    /** 系统程序版本：x-cdmi-specification-version */
    public static final String CDMI_VERSION = "x-cdmi-specification-version";
    
//    /** ETag matching constraint header for the copy object request */
//    public static final String COPY_SOURCE_IF_MATCH = "x-cdmi-copy-source-if-match";
//
//    /** ETag non-matching constraint header for the copy object request */
//    public static final String COPY_SOURCE_IF_NO_MATCH = "x-cdmi-copy-source-if-none-match";
//
//    /** Unmodified since constraint header for the copy object request */
//    public static final String COPY_SOURCE_IF_UNMODIFIED_SINCE = "x-cdmi-copy-source-if-unmodified-since";
//
//    /** Modified since constraint header for the copy object request */
//    public static final String COPY_SOURCE_IF_MODIFIED_SINCE = "x-cdmi-copy-source-if-modified-since";
    
    /*
     * Amazon HTTP Headers
     */

    /** Prefix for general Amazon headers: x-amz- */
    public static final String AMAZON_PREFIX = "x-amz-";

    /** S3's canned ACL header: x-amz-acl */
    public static final String S3_CANNED_ACL = "x-amz-acl";

    /** Amazon's alternative date header: x-amz-date */
    public static final String S3_ALTERNATE_DATE = "x-amz-date";

    /** Prefix for S3 user metadata: x-amz-meta- */
    public static final String S3_USER_METADATA_PREFIX = "x-amz-meta-";

    /** S3's version ID header */
    public static final String S3_VERSION_ID = "x-amz-version-id";

    /** S3's Multi-Factor Authentication header */
    public static final String S3_MFA = "x-amz-mfa";

    /** S3 response header for a request's extended debugging ID */
    public static final String EXTENDED_REQUEST_ID = "x-amz-id-2";

    /** S3 request header indicating how to handle metadata when copying an object */
    public static final String METADATA_DIRECTIVE = "x-amz-metadata-directive";

    /** DevPay token header */
    public static final String SECURITY_TOKEN = "x-amz-security-token";

    /** Header describing what class of storage a user wants */
    public static final String STORAGE_CLASS = "x-amz-storage-class";
    
    /** ETag matching constraint header for the copy object request */
    public static final String COPY_SOURCE_IF_MATCH = "x-amz-copy-source-if-match";

    /** ETag non-matching constraint header for the copy object request */
    public static final String COPY_SOURCE_IF_NO_MATCH = "x-amz-copy-source-if-none-match";

    /** Unmodified since constraint header for the copy object request */
    public static final String COPY_SOURCE_IF_UNMODIFIED_SINCE = "x-amz-copy-source-if-unmodified-since";

    /** Modified since constraint header for the copy object request */
    public static final String COPY_SOURCE_IF_MODIFIED_SINCE = "x-amz-copy-source-if-modified-since";

    /** Modified since constraint header for the get object request */
    public static final String GET_OBJECT_IF_MODIFIED_SINCE = "If-Modified-Since";

    /** Unmodified since constraint header for the get object request */
    public static final String GET_OBJECT_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    /** ETag matching constraint header for the get object request */
    public static final String GET_OBJECT_IF_MATCH = "If-Match";

    /** ETag non-matching constraint header for the get object request */
    public static final String GET_OBJECT_IF_NONE_MATCH = "If-None-Match";

    /** Encrypted symmetric key header that is used in the envelope encryption mechanism */
    public static final String CRYPTO_KEY = "x-amz-key";
    
    /** Initialization vector (IV) header that is used in the symmetric and envelope encryption mechanisms */
    public static final String CRYPTO_IV = "x-amz-iv";

    /** JSON-encoded description of encryption materials used during encryption */ 
    public static final String MATERIALS_DESCRIPTION = "x-amz-matdesc";
    
    /** Instruction file header to be placed in the metadata of instruction files */
    public static final String CRYPTO_INSTRUCTION_FILE = "x-amz-crypto-instr-file";
    
    
    /** ==================================================================**/

}
