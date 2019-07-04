class ConverResult {
  constructor(errors, data) {
    this.data = data || null;
    this.errors = errors || [];
  }
}

export default ConverResult;
