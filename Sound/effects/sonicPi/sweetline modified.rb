

bass_e = [52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65]
bass_a = [57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70]
bass_d = [62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75]
bass_g = [67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80]


define :sweetline do |sleeper|
  with_synth :fm do
    sweetline_list = [ bass_e[7], bass_a[5], bass_d[4], bass_a[5], bass_e[5], bass_a[4], bass_a[7], bass_a[4], bass_e[5], bass_a[4], bass_a[7], bass_d[6], bass_d[7], bass_d[6], bass_a[7], bass_a[5], bass_a[7], bass_d[5], bass_g[4], bass_d[5], bass_a[5], bass_d[4], bass_d[7], bass_d[4], bass_a[5], bass_d[4], bass_d[7], bass_g[6], bass_g[7], bass_g[6], bass_d[7], bass_d[5]]
    
    play_pattern_timed sweetline_list, [sleeper, 0.4]
    
  end
end




sweetline(0.6)