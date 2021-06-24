FROM nginx:1.21.0-alpine
COPY --chown=nginx:nginx docker/nginx/default.conf.template /etc/nginx/templates/default.conf.template
COPY --chown=nginx:nginx build /usr/share/nginx/html