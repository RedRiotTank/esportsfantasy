FROM node:18-alpine3.17 AS angular

WORKDIR /app

COPY . .

RUN npm install

RUN npm run build

CMD [ "node", "/app/dist/esportsfantasyfe/server/server.mjs" ]