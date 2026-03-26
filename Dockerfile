# ═══════════════════════════════════════════════════
# ÉTAPE 1 : Construction de l'APK (image temporaire)
# ═══════════════════════════════════════════════════
FROM eclipse-temurin:17-jdk-jammy AS builder

RUN apt-get update && apt-get install -y \
    wget \
    unzip && \
    rm -rf /var/lib/apt/lists/*

ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools

RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip \
    -O /tmp/cmdline-tools.zip && \
    unzip /tmp/cmdline-tools.zip -d ${ANDROID_HOME}/cmdline-tools && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest && \
    rm /tmp/cmdline-tools.zip

RUN yes | sdkmanager --licenses && \
    sdkmanager "platforms;android-35" "build-tools;35.0.0" "platform-tools"

WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew assembleDebug --no-daemon

# ═══════════════════════════════════════════════════
# ÉTAPE 2 : Image finale légère (seulement l'APK)
# ═══════════════════════════════════════════════════
FROM alpine:latest

WORKDIR /output

# Copier uniquement l'APK depuis l'étape 1
COPY --from=builder /app/app/build/outputs/apk/debug/app-debug.apk .

CMD ["echo", "APK disponible dans /output/app-debug.apk"]