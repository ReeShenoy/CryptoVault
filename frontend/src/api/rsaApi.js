import apiClient from './client';

export const generateRsaKeys = async () => {
  const response = await apiClient.post('/rsa/generate-keys');
  return response.data;
};

export const rsaEncrypt = async (plaintext, publicKey) => {
  const response = await apiClient.post('/rsa/encrypt', { plaintext, publicKey });
  return response.data;
};

export const rsaDecrypt = async (ciphertext, privateKey) => {
  const response = await apiClient.post('/rsa/decrypt', { ciphertext, privateKey });
  return response.data;
};
