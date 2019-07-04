import fetch from 'node-fetch';
import { assign, get, omit } from 'lodash';
import storage from 'node-persist';
import { ACL_API } from 'api/constant';
import { HTTP_STATUS } from 'constants/http';
import config from '../../config';
import {
  resultHandle,
  errorHandle,
  handleRequest,
  getRequestToken,
} from '../utils';
import { getDb } from '../dbs';

const CREATE_APPLICATION_API = '/financing/v1/applications';
const saveContract = async (id, contractInfo, bestOffer, creditAmount) => {
  const database = await getDb();
  const contracts = database.collection('contracts');
  const contract = {
    id,
    contractInfo,
    bestOffer,
    creditAmount,
    createAt: new Date(),
  };
  // Insert some users
  await contracts.insertOne(contract);
};

const creteApplicationFetch = async firstBodInfo => {
  const { url } = config.auth.oapi;
  const token = await storage.getItem('clz_token');
  const {
    bestOffer,
    firstName,
    lastName,
    middleName,
    gender,
    dayOfBirth,
    phoneNumber,
    idCard,
    monthlyIncome,
    monthlyPayment,
    drivingLicence,
    familyBook,
    userImage,
    retailAgent,
    verificationID,
    creditAmount,
    sessionID,
  } = firstBodInfo;

  const body = {
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
          firstName,
          lastName,
          middleName: middleName || '.',
        },
        gender,
        birthDate: dayOfBirth,
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
            number: idCard,
          },
        ],
      },
      additionalDocuments: [
        {
          documentType: 'PHOTO_PERSON',
          documentInfo: [],
          filename: 'userImage.png',
          content: userImage.replace(/^data:image\/[a-z]+;base64,/, ''),
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
          amount: monthlyIncome,
          currency: 'VND',
        },
        monthlyPaymentLoan: {
          amount: monthlyPayment || 0,
          currency: 'VND',
        },
      },
    },
  };

  // Add retail agent
  if (retailAgent) {
    body.application = assign(body.application, { retailAgent });
  }

  // Add Driver License & Family Book
  if (drivingLicence) {
    body.application.applicantPerson.identificationDocuments.push({
      type: 'DRIVERS_LICENSE',
      number: drivingLicence,
    });
  }

  if (familyBook) {
    body.application.applicantPerson.identificationDocuments.push({
      type: 'FAMILY_BOOK',
      number: familyBook,
    });
  }

  const result = await fetch(`${url}${CREATE_APPLICATION_API}`, {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
  });

  const json = await result.json();

  // Save Contract to DB
  const { applicationCode } = json;
  const storeApplication = omit(body, 'application.additionalDocuments');
  applicationCode &&
    (await saveContract(
      applicationCode,
      storeApplication,
      bestOffer,
      creditAmount,
    ));

  return {
    status: result.status,
    data: json,
  };
};

const checkAppByPhoneNumber = (phoneNumber, token) =>
  fetch(`${ACL_API}/application/${phoneNumber}`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

export default async (req, res) => {
  const { body, headers } = req;
  const phoneNumber = get(body, 'phoneNumber');
  const token = getRequestToken(headers);
  try {
    const [checkResult, error] = await handleRequest(checkAppByPhoneNumber, [
      phoneNumber,
      token,
    ]);

    if (checkResult.status !== HTTP_STATUS.OK) {
      resultHandle(res, checkResult);

      return;
    } else if (error) {
      errorHandle(res, error);

      return;
    }

    const result = await creteApplicationFetch(body);
    resultHandle(res, result);
  } catch (err) {
    errorHandle(res, err);
  }
};
