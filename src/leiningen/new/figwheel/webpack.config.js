module.exports = {
    entry: "./resources/public/js/compiled/out/index.js",
    output: {
	path: __dirname + "/resources/public/js/compiled",
	filename: "{{sanitized}}.js"
    }
}
