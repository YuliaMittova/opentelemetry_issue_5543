on_pull_request to: develop, {
  static_code_analysis()
}

on_change to: develop, {
  static_code_analysis()
  build_in dev
  deploy_to dev
}

on_pull_request to: production, from: develop, {
  static_code_analysis()
}

on_change to: production, {
  static_code_analysis()
  build_in prod
  deploy_to prod
}
