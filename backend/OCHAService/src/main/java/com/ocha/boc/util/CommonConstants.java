package com.ocha.boc.util;

public class CommonConstants {

    /**
     * The FAILED status of the response object
     */
    public static final String STR_FAIL_STATUS = "FAILED";
    /**
     * The SUCCESS status of the response object
     */
    public static final String STR_SUCCESS_STATUS = "SUCCESS";

    /**
     * Category Error:
     */
    public static final String CREATE_NEW_CATEGORY_FAIL                 = "Không thể tạo mới danh mục. Lỗi hệ thống !";

    public static final String UPDATE_CATEGORY_FAIL                     = "Không thể cập nhật cho danh mục này. Lỗi hệ thống";

    public static final String CATEGORY_NAME_IS_NULL                    = "Không thể tìm thấy danh mục này.";

    public static final String GET_ALL_CATEGORY_FAIL                    = "Không tìm thấy thông tin danh mục. Lỗi hệ thống !";

    public static final String DELETE_CATEGORY_BY_CATEGORY_ID_FAIL      = "Không thể xóa danh mục này. Lỗi hệ thống !";

    public static final String GET_DOANH_THU_THEO_DANH_MUC_FAIL         = "Có lỗi trong quá trình xử lý doanh thu theo danh mục";

    public static final String CATEGORY_IS_EXISTED                      = "Danh mục này đã có trong hệ thống.";

    /**
     * PRODUCT Error:
     */
    public static final String CREATE_NEW_PRODUCT_FAIL                 = "Không thể tạo mới mặt hàng. Lỗi hệ thống !";

    public static final String UPDATE_PRODUCT_FAIL                     = "Không thể cập nhật thông tin cho mặt hàng này. Lỗi hệ thống !";

    public static final String PRODUCT_IS_NULL                         = "Không thể tìm thấy mặt hàng này.";

    public static final String GET_ALL_PRODUCT_FAIL                    = "Không tìm thấy thông tin mặt hàng. Lỗi hệ thống!";

    public static final String DELETE_PRODUCT_BY_PRODUCT_ID_FAIL      = "Không thể xóa thông tin mặt hàng này. Lỗi hệ thống!";

    public static final String PRODUCT_IS_EXISTED                      = "Mặt hàng này đã có trong hệ thống.";
    /**
     * Nguyên liệu Error:
     */
    public static final String CREATE_NEW_NGUYEN_LIEU_FAIL              = "Không thể tạo mới nguyên liệu. Lỗi hệ thống!";

    public static final String UPDATE_NGUYEN_LIEU_FAIL                  = "Không thể cập nhật thông tin cho nguyên liệu này. Lỗi hệ thống!";

    public static final String NGUYEN_LIEU_IS_NULL                      = "Không thể tìm thấy nguyên liệu này.";

    public static final String GET_ALL_NGUYEN_LIEU_FAIL                 = "Không tìm thấy thông tin nguyên liệu. Lỗi hệ thống!";

    public static final String DELETE_NGUYEN_LIEU_BY_NGUYEN_LIEU_ID_FAIL= "Không thể xóa thông tin nguyên liệu này. Lỗi hệ thống!";

    public static final String NGUYEN_LIEU_EXISTED                      = "Nguyên liệu này đã có trong hệ thống.";

    /**
     * User Error:
     */
    public static final String CREATE_NEW_USER_FAIL                     = "Không thể tạo mới người dùng. Lỗi hệ thống!";

    public static final String UPDATE_USER_FAIL                         = "Không thể cập nhật thông tin người dùng này. Lỗi hệ thống!";

    public static final String USER_IS_NULL                             ="Người dùng không tồn tại.";

    public static final String USER_EXISTED                             = "Người dùng đã tồn tại trong hệ thống. Không thể tạo mới!";

    public static final String GET_ALL_USER_FAIL                        = "Hệ thống không tồn tại người dùng nào !";

    public static final String SEND_VERIFICATION_CODE_FAIL              = "Lỗi trong quá trình gửi mã xác thực.";

    public static final String VERIFICATION_CODE_FAIL                   = "Mã xác thực không đúng hoặc đã hết hạn. Vui lòng đăng nhập lại";

    public static final String USER_NOT_EXISTED                         = "Người dùng không tồn tại trong hệ thống !";

    public static final String LOGIN_FAIL                               = "Không thể đăng nhập vào hệ thống !";

    public static final String ACCOUNT_IS_NOT_ACTIVE                    = "Tài khoản chưa được kích hoạt. Không thể đăng nhập";

    /**
     * Restaurant Error:
     */
    public static final String  CREATE_NEW_RESTAURANT_FAIL                = "Không thể tạo mới thông tin cửa hàng này";

    public static final String  UPDATE_RESTAURANT_ID_ON_USER_FAIL         = "Không thể cập nhật thông tin cửa hàng cho tài khoản";

    public static final String  UPDATE_RESTAURANT_FAIL                    = "Lỗi trong quá trình cập nhật thông tin cửa hàng";

    public static final String  UPDATE_EMAIL_RESTAURANT_FAIL              = "Không có thông tin email. Không thể cập nhật thông tin email cho cửa hàng";

    public static final String  RESTAURANT_IS_NOT_EXISTED                 = "Không tìm thấy thông tin của cửa hàng. ";

    /**
     * Nhân Viên Error:
     */
    public static final String CREATE_NEW_EMPLOYEE_FAIL                = "Không thể tạo mới thông tin nhân viên này.";

    public static final String USERNAME_EXISTED                         = "Username này đã tồn tại trong hệ thống. Xin chọn lại tên khác";

    public static final String GET_LIST_EMPLOYEE_BY_RESTAURANT_ID_FAIL   = "Lỗi khi tìm kiếm thông tin nhân viên của cửa hàng.";

    public static final String NOT_EXISTED_ANY_EMPLOYEE                = "Cửa hàng không có thông tin của nhân viên nào";

    public static final String DELETE_EMPLOYEE_BY_EMPLOYEE_ID_FAIL    = "Lỗi khi xóa nhân viên này khỏi cửa hàng.";

    public static final String EMPLOYEE_IS_NOT_EXISTED                 = "Nhân viên này không tồn tại. Không thể xóa khỏi hệ thống";

    /**
     * Giảm Giá Error:
     */
    public static final String CREATE_NEW_DISCOUNT_FAIL                 = "Lỗi trong quá trình tạo mới mã giảm giá.";

    public static final String DELETE_DISCOUNT_BY_ID_FAIL               = "Lỗi trong quá trình xóa mã giảm giá.";

    public static final String UPDATE_DISCOUNT_FAIL                     = "Lỗi trong quá trình cập nhật mã giảm giá";

    /**
     * Order Error:
     */
    public static final String INITIAL_ORDER_FAIL                       = "Lỗi trong quá trình tạo đơn hàng.";

    public static final String UPDATE_ORDER_FAIL                        = "Lỗi trong quá trình cập nhật đơn hàng.";

    public static final String ORDER_NOT_EXISTED                        = "Không có thông tin đơn hàng trong hệ thống.";

    public static final String ORDER_CHECKOUT_FAIL                      = "Lỗi trong quá trình thanh toán đơn hàng.";

    public static final String GET_ORDERS_BY_CUAHANGID_FAIL             = "Lỗi trong quá trình truy xuất thông tin đơn hàng";

    /**
     * Báo cáo
     */
    public static final String GET_DOANH_THU_TONG_QUAN_FAIL             = "Lỗi trong quá trình xử lý doanh thu tổng quản.";

    public static final String GET_DOANH_THU_TONG_QUAN_IN_RANGE_DATE_FAIL= "Không có thông tin về doanh thu.";

    public static final String GET_BAO_CAO_DOANH_THU_THEO_DANH_MUC_FAIL  = "Lỗi trong quá trình lấy thông tin doanh thu theo danh mục";

    public static final String GET_MAT_HANG_BAN_CHAY_FAIL                = "Lỗi trong quá trình lấy thông tin mặt hàng bán chạy";

    public static final String GET_BAO_CAO_GIAM_GIA_FAIL                 = "Lỗi trong quá trình lấy báo cáo giảm giá";

    public static final String GET_BAO_CAO_DOANH_THU_THEO_NHAN_VIEN_FAIL = "Lỗi trong quá trình lấy báo cáo doanh thu theo nhân viên.";

    /**
     * System Configuration
     */
    public static final String GET_ALL_GIAY_IN_SYSTEM_CONFIGURATION_FAIL  = "Lỗi trong quá trình lấy thông tin thiết lập giấy in của hệ thống.";

    public static final String CREATE_NEW_GIAY_IN_INFORMATION_FAIL        = "Lỗi trong quá trình tạo mới giấy in hệ thống.";

    public static final String DELETE_GIAY_IN_BY_TITLE_FAIL               = "Lỗi trong quá trình xóa thông tin giấy in trong hệ thống.";

    public static final String GET_ALL_DANH_MUC_SAN_PHAM_FAIL             = "Lỗi trong quá trình lấy thông tin danh mục sản phẩm.";

    public static final String GET_ALL_DANH_MUC_SAN_PHAM_DOESNT_EXISTED   = "Không có thông tin danh mục sản phẩm trong hệ thống.";

    public static final String GET_ALL_MO_HINH_KINH_DOANH_FAIL            = "Lỗi trong quá trình lấy thông tin mô hình kinh doanh";

    public static final String GET_ALL_MO_HINH_KINH_DOANH_DOESNT_EXISTED  = "Không có thông tin mô hình kinh doanh trong hệ thống";


}
