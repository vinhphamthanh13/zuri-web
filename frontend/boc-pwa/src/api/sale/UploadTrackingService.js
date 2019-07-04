import UploadTrackingModel from './UploadTrackingModel';

const UploadDocTrackingName = 'saleUploadTracking';
class UploadTrackingService {
  constructor(db) {
    this.db = db;
  }

  async add(data, auth) {
    if (!data) {
      return;
    }

    const trackings = this.db.collection(UploadDocTrackingName);
    const tracking = new UploadTrackingModel(data, auth ? auth.username : '');
    await trackings.insertOne(tracking);
  }
}

export default UploadTrackingService;
