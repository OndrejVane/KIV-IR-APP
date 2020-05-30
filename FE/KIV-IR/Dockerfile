### STAGE 1: BUILD ###
FROM node:13.10.1-alpine as build-stage
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

### STAGE 2: RUN ###
FROM nginx:1.17.9-alpine as prod-stage
COPY ./default.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /app/dist/fulltextsearch /usr/share/nginx/html
EXPOSE 80
