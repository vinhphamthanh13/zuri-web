import { ACL_API } from 'api/constant';

const LOAN_OFFER = 'loan-offer';
const USER_INFO = 'personal-info';
const SOCIAL_INFO = 'income-info';
const SUMMARY = 'summary';

export const URL = {
  [LOAN_OFFER]: LOAN_OFFER,
  [USER_INFO]: USER_INFO,
  [SOCIAL_INFO]: SOCIAL_INFO,
  [SUMMARY]: SUMMARY,
};

export const TRACKING_URL = {
  [LOAN_OFFER]: `${ACL_API}/offer`,
  [SOCIAL_INFO]: `${ACL_API}/income`,
  [USER_INFO]: `${ACL_API}/personal`,
  [SUMMARY]: `${ACL_API}/summary`,
};
