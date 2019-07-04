import moment from 'moment';

export const DEFAULT_IMG =
  'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAIAAAC0Ujn1AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAwSURBVEhL7cyhDQAACAQx9p/mh8KzAv48guSS6tZ0jliDNViDNViDNViDNVjDx7qz+QrVBTtLbiAAAAAASUVORK5CYII=';

export const getFakeApplicationBody = ({
  phoneNumber,
  retailAgent,
  sessionID,
  verificationID,
  bestOffer,
}) => ({
  offerCode: bestOffer.offerCode,
  userStatistics: [
    {
      key: 'session_id',
      value: sessionID,
    },
  ],
  application: {
    applicantPerson: {
      name: {
        firstName: 'FirstName',
        lastName: 'LastName',
        middleName: 'ETWO',
      },
      gender: 'MALE',
      birthDate: moment()
        .subtract(20, 'y')
        .format('YYYY-MM-DD'),
      emails: [],
      addresses: [],
      phoneNumbers: [
        {
          verificationID,
          phoneType: 'PRIMARY_MOBILE',
          number: phoneNumber,
        },
      ],
      identificationDocuments: [
        {
          type: 'ID_CARD',
          number: '000000000',
        },
        {
          type: 'DRIVERS_LICENSE',
          number: 111111,
        },
      ],
    },
    additionalDocuments: [
      {
        documentType: 'PHOTO_PERSON',
        documentInfo: [],
        filename: 'userImage.png',
        content: DEFAULT_IMG.replace(/^data:image\/[a-z]+;base64,/, ''),
        photoTakingResult: 'SAVED_COMPONENT',
      },
    ],
    relatedPersons: [],
    disbursementInfo: {
      method: 'PARTNER_BANK',
      salesroomCode: '011505',
    },
    employmentInfo: {
      monthlyIncome: {
        amount: 20000000,
        currency: 'VND',
      },
      monthlyPaymentLoan: {
        amount: 0,
        currency: 'VND',
      },
    },
    retailAgent,
  },
});
