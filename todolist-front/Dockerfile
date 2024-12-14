FROM node:lts-alpine

COPY . /opt/app

WORKDIR /opt/app

EXPOSE 4173

ARG BACKEND_HOST
RUN npm install
RUN VITE_API_URL=$BACKEND_HOST npm run build

ENTRYPOINT ["npm", "run", "serve", "--", "--host"]
