# ──────────────────────────────────────────────────
# Dockerfile pour construire l'APK Android
# ──────────────────────────────────────────────────
FROM openjdk:17-jdk-slim

# Installer les outils nécessaires
RUN apt-get update && apt-get install -y \
    wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Variables d'environnement Android SDK
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH=$PATH:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools

# Installer Android SDK Command-line Tools
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip \
    -O /tmp/cmdline-tools.zip && \
    unzip /tmp/cmdline-tools.zip -d ${ANDROID_HOME}/cmdline-tools && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest && \
    rm /tmp/cmdline-tools.zip

# Accepter les licences et installer les composants nécessaires
RUN yes | sdkmanager --licenses && \
    sdkmanager "platforms;android-35" "build-tools;35.0.0" "platform-tools"

# Répertoire de travail
WORKDIR /app

# Copier les fichiers du projet
COPY . .

# Donner les permissions d'exécution au Gradle Wrapper
RUN chmod +x ./gradlew

# Construire l'APK debug
RUN ./gradlew assembleDebug --no-daemon

# Commande par défaut
CMD ["echo", "✅ APK construit dans : app/build/outputs/apk/debug/"]