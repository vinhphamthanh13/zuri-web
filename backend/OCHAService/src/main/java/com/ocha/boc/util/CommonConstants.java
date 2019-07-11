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
     * Khuyến Mãi Error:
     */

    public static final String CREATE_NEW_KHUYEN_MAI_FAIL               = "Không thể tạo mới mã khuyến mãi. Lỗi hệ thống";

    public static final String GET_KHUYEN_MAI_BY_KHUYEN_MAI_ID_FAIL     = "Không tìm thấy khuyến mãi theo yêu cầu. Lỗi hệ thống";

    public static final String GET_ALL_KHUYEN_MAI_FAIL                  = "Không tìm thấy thông tin khuyến mãi. Lỗi hệ thống";

    public static final String DELETE_KHUYEN_MAI_BY_KHUYEN_MAI_ID_FAIL  = "Không thể xóa mã khuyến mãi này. Lỗi hệ thống";

    public static final String UPDATE_KHUYEN_MAI_FAIL                   = "Không thể cập nhật cho mã khuyến mãi này. Lỗi hệ thống";

    public static final String GET_LIST_ORDER_BY_DATE_FAIL              = "Không có dữ liệu hóa đơn. Lỗi hệ thống!";

    public static final String KHUYEN_MAI_IS_EXISTED                    = "Khuyến mãi này đã có trong hệ thống.";

    /**
     * Danh Mục Error:
     */
    public static final String CREATE_NEW_DANH_MUC_FAIL                 = "Không thể tạo mới danh mục. Lỗi hệ thống !";

    public static final String UPDATE_DANH_MUC_FAIL                     = "Không thể cập nhật cho danh mục này. Lỗi hệ thống";

    public static final String DANH_MUC_NAME_IS_NULL                    = "Không thể tìm thấy danh mục này.";

    public static final String GET_ALL_DANH_MUC_FAIL                    = "Không tìm thấy thông tin danh mục. Lỗi hệ thống !";

    public static final String DELETE_DANH_MUC_BY_DANH_MUC_ID_FAIL      = "Không thể xóa danh mục này. Lỗi hệ thống !";

    public static final String GET_DOANH_THU_THEO_DANH_MUC_FAIL         = "Có lỗi trong quá trình xử lý doanh thu theo danh mục";

    public static final String DANH_MUC_IS_EXISTED                      = "Danh mục này đã có trong hệ thống.";

    /**
     * Bảng giá Error:
     */
    public static final String CREATE_NEW_BANG_GIA_FAIL                 = "Không thể tạo mới thông tin bảng giá. Lỗi hệ thống !";

    public static final String UPDATE_BANG_GIA_FAIL                     = "Không thể cập nhật thông tin cho bảng giá này. Lỗi hệ thống !";

    public static final String BANG_GIA_IS_NULL                         = "Không thể tìm thấy bảng giá này.";

    public static final String GET_ALL_BANG_GIA_FAIL                    = "Không tìm thấy thông tin bảng giá. Lỗi hệ thống !";

    public static final String DELETE_BANG_GIA_BY_BANG_GIA_ID_FAIL      = "Không thể xóa bảng giá này. Lỗi hệ thống !";

    /**
     * Mặt hàng Error:
     */
    public static final String CREATE_NEW_MAT_HANG_FAIL                 = "Không thể tạo mới mặt hàng. Lỗi hệ thống !";

    public static final String UPDATE_MAT_HANG_FAIL                     = "Không thể cập nhật thông tin cho mặt hàng này. Lỗi hệ thống !";

    public static final String MAT_HANG_IS_NULL                         = "Không thể tìm thấy mặt hàng này.";

    public static final String GET_ALL_MAT_HANG_FAIL                    = "Không tìm thấy thông tin mặt hàng. Lỗi hệ thống!";

    public static final String DELETE_MAT_HANG_BY_MAT_HANG_ID_FAIL      = "Không thể xóa thông tin mặt hàng này. Lỗi hệ thống!";

    public static final String MAT_HANG_IS_EXISTED                      = "Mặt hàng này đã có trong hệ thống.";
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

}
