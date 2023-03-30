# build environment
FROM node:18-alpine as builder
WORKDIR /app
COPY package.json .
COPY yarn-lock.json .
RUN yarn
COPY . .
RUN yarn
RUN yarn run build

# production environment
FROM nginx:1.21.0-alpine as production
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
