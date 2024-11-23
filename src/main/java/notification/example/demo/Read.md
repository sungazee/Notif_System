# Système de Gestion des Notifications

## Introduction
Ce projet est un système de gestion des notifications conçu pour envoyer des notifications par divers canaux, tels que les emails et les SMS. Le système est construit en utilisant Spring Batch pour le traitement par lots et intègre des API externes pour les services d'email et de SMS.

## Prérequis
- Java 17
- Spring Boot 2.7.x
- Maven 3.8+
- MySQL 8.0+
- API Twilio pour les SMS
- JavaMailSender pour les emails

## Installation

### 1. Cloner le dépôt

Commencez par cloner le dépôt GitHub sur votre machine locale :

### bash
git clone https://github.com/------/systeme-gestion-notifications.git
cd systeme-gestion-notifications



## Documentation Technique du Système de Gestion des Notifications

## Introduction
Ce document décrit en détail le système de gestion des notifications que j'ai développé. Ce système permet l'envoi de notifications via différents canaux, tels que les emails et les SMS, en utilisant des technologies telles que Spring Batch, JavaMailSender, et l'API Twilio. Le projet est structuré autour de l'intégration de ces services avec une base de données MySQL pour gérer les processus de notification de manière efficace et fiable.

## Structure du Projet

### 1. Modèle de Données

Le projet comprend plusieurs tables dans la base de données, dont les principales sont :

- **Notification** : Stocke les informations sur les notifications à envoyer (id, canal d'envoi, date d'envoi, etc.).
- **Log** : Garde une trace des notifications envoyées, y compris des informations sur le destinataire, le type de notification, le message, et l'état de l'envoi.

### 2. Classes Principales

#### a. **NotificationController**
Cette classe gère les requêtes HTTP liées aux notifications. Elle est responsable de l'initialisation des processus de notification, qu'ils soient envoyés individuellement ou par lots. Les méthodes clés incluent :

- `getAllNotifications()` : Récupère toutes les notifications stockées.
- `sendNotifications()` : Déclenche l'envoi des notifications selon le canal spécifié.

#### b. **MailUtil**
`MailUtil` est une classe utilitaire dédiée à l'envoi d'emails. Elle utilise le service `JavaMailSender` de Spring pour envoyer des emails. Les principales fonctionnalités incluent :

- **sendEmail()** : Méthode principale pour envoyer un email à un destinataire. Cette méthode s'appuie sur les configurations définies dans le fichier `application.properties` et assure la journalisation des opérations via le service `NotificationLogService`.

#### c. **TwilioSmsSender**
Cette classe est responsable de l'envoi de SMS en utilisant l'API Twilio. Elle est configurée pour établir une connexion sécurisée avec Twilio et envoyer des SMS. Les composants principaux comprennent :

- **sendSms()** : Envoie un SMS au destinataire spécifié, avec journalisation de l'état de l'envoi.

#### d. **Spring Batch Configuration**
Le projet utilise Spring Batch pour gérer les envois en masse de notifications. Deux `ItemReader` sont utilisés pour lire les données de notifications :

- **CSV ItemReader** : Lit les notifications à partir d'un fichier CSV, ignore la première ligne (en-tête), et stocke les données dans la base de données.
- **Database ItemReader** : Lit les notifications depuis la base de données, en récupérant soit toutes les notifications, soit uniquement celles qui ne sont pas encore envoyées.

### 3. Documentation Technique et Commentaires

Afin de faciliter la maintenance et l'évolution du projet, j'ai intégré une documentation détaillée dans le code lui-même ainsi que dans un fichier README.

#### a. **README**
Le fichier `README.md` contient les informations essentielles sur le projet, notamment :

- **Installation et Configuration** : Instructions détaillées sur la configuration du projet, y compris les dépendances, la configuration de la base de données, et les services externes.
- **Exécution** : Étapes pour exécuter l'application, y compris la manière de lancer les jobs Spring Batch.
- **API Endpoints** : Détails sur les points de terminaison disponibles pour interagir avec le système de notifications.

#### b. **Commentaires dans le Code**
Chaque classe, méthode et composant clé du projet est commenté pour expliquer son rôle et son fonctionnement. Ces commentaires sont destinés à aider les développeurs à comprendre rapidement les fonctionnalités du code, les choix de conception, et les points critiques à surveiller.

### 4. Gestion des Erreurs et Débogage

L'application inclut des mécanismes robustes pour la gestion des erreurs. Les exceptions rencontrées lors de l'envoi des notifications sont capturées et enregistrées dans le `Log` avec des détails sur l'erreur. Cela permet une analyse post-mortem et un débogage efficace.

### 5. Challenges Techniques et Solutions

Pendant le développement du projet, plusieurs défis techniques ont été rencontrés, tels que :

- **Problèmes de Connexion à la Base de Données** : Il a été nécessaire de reconfigurer les paramètres de connexion pour s'assurer que l'application pouvait se connecter de manière fiable à la base de données MySQL.
- **Intégration des Bibliothèques Externes** : La configuration de bibliothèques telles que `JavaMailSender` et l'API Twilio a nécessité une compréhension approfondie des dépendances et des configurations nécessaires pour garantir leur bon fonctionnement.
- **Apprentissage de Nouveaux Langages et Outils** : L'apprentissage et la mise en œuvre de Spring Batch ont été un défi, mais cela a permis de structurer le traitement par lots de manière efficace.

## Conclusion

Ce projet de gestion des notifications a été conçu pour être extensible, modulaire, et facile à maintenir. Les choix technologiques et les méthodologies adoptées assurent non seulement la robustesse du système mais également sa capacité à évoluer en fonction des besoins futurs. La documentation complète, tant technique que dans le code, vise à rendre ce projet accessible à d'autres développeurs et à garantir une continuité dans son développement.

---

Ce document détaille chaque aspect technique du projet, de l'architecture logicielle à la gestion des erreurs, en passant par la documentation et les défis rencontrés. Il peut servir de référence pour les futures étapes de développement ou pour la maintenance du projet.