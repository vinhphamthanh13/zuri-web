import { HTTP_STATUS } from '../constant';

class ResponseDto {
  constructor(data, status) {
    this.status = status || HTTP_STATUS.OK;
    this.data = data || null;
  }
}

export default ResponseDto;
