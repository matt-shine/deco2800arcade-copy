create database arcade;
use arcade;

CREATE USER arcadeserver IDENTIFIED BY 'arcadeserver';

grant usage on *.* to arcadeserver@localhost identified by 'arcadeserver';
grant all privileges on arcade.* to arcadeserver@localhost;