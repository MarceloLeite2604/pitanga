const domainValues = {
  min: 0,
  max: 6
};

export const mediumPoint = (domainValues.min + domainValues.max) / 2;

export const domainLimits = [domainValues.min, domainValues.max];

export const axisTicks = Array.from({ length: domainValues.max }, (_, index) => index)
  .filter(value => value !== mediumPoint);
