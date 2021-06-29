const local = {
  host: 'localhost',
  port: 8080,
  path: '/pitanga'
};

const production = {
  host: 'marceloleite2604-pitanga.herokuapp.com',
  port: 80,
  path: '/pitanga'
};

const config = process.env.REACT_APP_STAGE === 'production' ? production : local;

export default {
  ...config
};
