ARG NODE_VERSION=22.13.0

# ================= BASE =================
FROM node:${NODE_VERSION}-alpine AS base
WORKDIR /app

# fix network (tránh timeout npm)
RUN npm config set registry https://registry.npmmirror.com \
 && npm config set fetch-timeout 600000

# ================= DEPS =================
FROM base AS deps
COPY package.json package-lock.json ./
RUN npm ci

# ================= BUILD =================
FROM base AS build
COPY --from=deps /app/node_modules ./node_modules
COPY . .
RUN npm run build

# ================= RUN =================
FROM node:${NODE_VERSION}-alpine AS final
WORKDIR /app

# cài server static
RUN npm install -g serve

# 👉 đúng chỗ: dist
COPY --from=build /app/dist ./dist

EXPOSE 3000

CMD ["serve", "-s", "dist", "-l", "3000"]