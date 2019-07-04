import { CLZ_UPDATE_STEP } from 'constants/clz';
import { STEPS } from 'routes/clz/1bod/constant';

export default function step(state, action) {
  const newState = state || STEPS.WELCOME_PAGE;
  if (action.type === CLZ_UPDATE_STEP) {
    return action.payload;
  }

  return newState;
}
