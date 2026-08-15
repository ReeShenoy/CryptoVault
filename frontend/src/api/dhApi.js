// import apiClient from './client';

// export const generateDhKeys = async () => {
//   const response = await apiClient.post('/dh/generate-keys');
//   return response.data;
// };

// export const generateDhSharedSecret = async ({
//   alicePrivateKey,
//   alicePublicKey,
//   bobPrivateKey,
//   bobPublicKey,
// }) => {
//   const response = await apiClient.post('/dh/generate-shared-secret', {
//     alicePrivateKey,
//     alicePublicKey,
//     bobPrivateKey,
//     bobPublicKey,
//   });
//   return response.data;
// };

import apiClient from './client';

export const processDhMessages = async ({ aliceMessage, bobMessage }) => {
  const response = await apiClient.post('/dh/process', {
    aliceMessage,
    bobMessage,
  });

  return response.data;
};