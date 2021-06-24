FROM nginx:1.20.0-alpine
COPY --chown=nginx:nginx docker/nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --chown=nginx:nginx build /usr/share/nginx/html