const local = {
  backendUri: 'ws://localhost:8080/pitanga'
};

const production = {
  backendUri: 'ws://marceloleite2604-pitanga.herokuapp.com/pitanga'
};

const config = process.env.REACT_APP_STAGE === 'production' ? production : local;

export default {
  ...config
};
