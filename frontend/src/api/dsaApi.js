import apiClient from './client';

export const generateDsaKeys = async () => {
  const response = await apiClient.post('/dsa/generate-keys');
  return response.data;
};

export const dsaSign = async (message, privateKey) => {
  const response = await apiClient.post('/dsa/sign', { message, privateKey });
  return response.data;
};

export const dsaVerify = async (message, signature, publicKey) => {
  const response = await apiClient.post('/dsa/verify', { message, signature, publicKey });
  return response.data;
};
