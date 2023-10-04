FROM node:20-alpine3.17
WORKDIR /opt/app
COPY ./gate-simulator .
RUN npm install
CMD ["npm", "start"]
EXPOSE 9999
