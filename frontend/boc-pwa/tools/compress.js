import execa from 'execa';
import pkg from '../package.json';

async function compress() {
  await execa('npm', ['install'], { cwd: 'build' });
  await execa('tar', ['-czf', `${pkg.name}-${pkg.version}.tgz`, 'build']);
}

export default compress;
