export const FILENAME_PLACEHOLDER = '';
export const UPLOAD_TITLE = 'Chọn file upload';
export const UPLOAD_BUTTON = 'Tải lên';
export const HEADER_TITLE = 'Dành cho quản trị viên BOCVN';
export const PAGE_TITLE = 'Cập nhật danh sách sales';
export const UPLOAD_NOTE = {
  title: 'Lưu ý:',
  notes: [
    { id: 1, text: 'Chỉ upload file CSV hoặc Excel' },
    {
      id: 2,
      text:
        'Các trường bắt buộc nhập trong file upload: SHOP_CODE, DSM_MAIL, SA_CODE, SA_MAIL',
    },
    {
      id: 3,
      text:
        'Đối với file CSV, các giá trị trong file được phân cách bởi dấu phẩy',
    },
  ],
};
export const MAX_SIZE_UPLOAD = 5 * 1024 * 1024;
export const ERROR_FILE_TYPE = 'Vui lòng chỉ upload file CSV hoặc Excel';
export const SUPPORTED_FILE = ['csv', 'xlsx'];
export const POPUP_ERROR = 'Thông tin này là bắt buộc!';
export const INPUT_PLACEHOLDER = 'Click vào để chọn file!';
export const FIELD_NAME = 'filename';
