export const validateEmailFormat = (email: string): boolean => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

export const validatePhoneNumber = (phone: string) => {
  const regex = /^(?:\+48|0048)?\d{9}$/;
  return regex.test(phone);
};
