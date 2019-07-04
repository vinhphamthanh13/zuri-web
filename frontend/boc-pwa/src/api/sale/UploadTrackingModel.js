class UploadTrackingModel {
  constructor(config, user) {
    const {
      fileName = null,
      fileSize = 0,
      status = null,
      numberOfRecord = 0,
      note = null,
      createAt = new Date().getTime(),
    } = config;

    this.fileName = fileName;
    this.fileSize = fileSize;
    this.status = status;
    this.numberOfRecord = numberOfRecord;
    this.note = note;
    this.createBy = user || null;
    this.createAt = createAt;
  }
}

export default UploadTrackingModel;
