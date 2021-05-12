module.exports = {
  env: {
    browser: true,
    es2021: true,
    jest: true
  },
  globals: {
    JSX: 'readonly'
  },
  extends: [
    'plugin:react/recommended',
    'standard'
  ],
  parser: '@typescript-eslint/parser',
  parserOptions: {
    ecmaFeatures: {
      jsx: true
    },
    ecmaVersion: 12,
    sourceType: 'module'
  },
  plugins: [
    'react',
    '@typescript-eslint'
  ],
  settings: {
    react: {
      version: 'detect'
    }
  },
  rules: {
    'no-unused-vars': 'off',
    '@typescript-eslint/no-unused-vars': ['error'],
    'no-use-before-define': 'off',
    '@typescript-eslint/no-use-before-define': ['error'],
    semi: ['error', 'always'],
    'space-before-function-paren': ['error', 'never'],
    'react/jsx-uses-react': 'off',
    'react/react-in-jsx-scope': 'off',
    'no-console': 'off',
    'padded-blocks': 'off',
    indent: 'off',
    '@typescript-eslint/indent': ['error', 2],
    'no-trailing-spaces': ['error', { skipBlankLines: true }],
    'react/prop-types': 'off',
    'no-debugger': 'off'
  }
};
