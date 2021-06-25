const local = {
  backendUri: 'ws://localhost:8080/pitanga'
};

const production = {
  backendUri: 'wss://marceloleite2604-pitanga.herokuapp.com:8080/pitanga'
};

const config = process.env.REACT_APP_STAGE === 'production' ? production : local;

export default {
  ...config
};
