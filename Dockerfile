FROM alpine:latest

WORKDIR /output

# Copier l'APK déjà construit
COPY app/build/outputs/apk/debug/app-debug.apk .

CMD ["echo", "APK disponible dans /output/app-debug.apk"]